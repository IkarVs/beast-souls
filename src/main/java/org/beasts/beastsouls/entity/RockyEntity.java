package org.beasts.beastsouls.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import org.beasts.beastsouls.entity.ai.RockyAttackGoal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RockyEntity extends PathAwareEntity implements GeoEntity {

    public static final Logger LOGGER = LoggerFactory.getLogger("beastsouls");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // -----------------------------
    //      MORT CUSTOM
    // -----------------------------
    private boolean isDying = false;
    private int deathTicks = 0;

    public RockyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    // -----------------------------
    //      ATTRIBUTS
    // -----------------------------
    public static DefaultAttributeContainer.Builder createRockyAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0);
    }

    // -----------------------------
    //      IA
    // -----------------------------
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new RockyAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, HostileEntity.class, true));
    }

    // -----------------------------
    //      ATTAQUE
    // -----------------------------
    @Override
    public boolean tryAttack(Entity target) {
        boolean success = super.tryAttack(target);

        if (success) {
            LOGGER.info("ATTACK DAMAGE");
            target.damage(
                    this.getDamageSources().mobAttack(this),
                    (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)
            );
            target.setVelocity(target.getVelocity().add(0, 0.4, 0));
        }

        return success;
    }

    // -----------------------------
    //      ANIMATIONS
    // -----------------------------
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        // 🎯 Mouvement (idle / walk)
        controllers.add(new AnimationController<>(
                this,
                "movement_controller",
                0,
                state -> {
                    if (this.isDying) return PlayState.STOP;

                    if (state.isMoving()) {
                        return state.setAndContinue(
                                RawAnimation.begin().thenLoop("walk")
                        );
                    }
                    return state.setAndContinue(
                            RawAnimation.begin().thenLoop("idle")
                    );
                }
        ));

        // 💥 Attaque (one-shot)
        controllers.add(new AnimationController<>(
                this,
                "attack_controller",
                0,
                state -> PlayState.STOP
        ).triggerableAnim(
                "attack",
                RawAnimation.begin().thenPlay("attack")
        ));

        // 💀 Mort (one-shot)
        controllers.add(new AnimationController<>(
                this,
                "death_controller",
                0,
                state -> PlayState.STOP
        ).triggerableAnim(
                "death",
                RawAnimation.begin().thenPlay("death")
        ));
    }

    // -----------------------------
    //      MORT CUSTOM
    // -----------------------------
    @Override
    public void onDeath(DamageSource source) {
        if (!this.isDying) {
            this.isDying = true;
            this.deathTicks = 0;
            LOGGER.info("ON DEATH");

            // Empêche la physique vanilla
            this.setNoGravity(true);
            this.setVelocity(0, 0, 0);
            this.velocityDirty = true; // garantit qu'aucun mouvement ne s'applique

            // Lance l’animation de mort
            this.triggerAnim("death_controller", "death");

            // On garde l'entité en vie visuellement mais invincible
            this.setHealth(1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isDying) {
            this.deathTicks++;

            // Bloque tous les mouvements
            this.setVelocity(0, 0, 0);
            this.velocityDirty = true;

            // Optionnel : force l’orientation pour éviter la chute
            this.setPitch(0);
            this.prevPitch = 0;

            // Supprime l’entité après 1.5s (30 ticks)
            if (this.deathTicks >= 30) {
                LOGGER.info("REMOVED");
                this.remove(RemovalReason.KILLED);
            }
        }
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}