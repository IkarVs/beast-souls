package org.beasts.beastsouls.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RockyEntity extends PathAwareEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RockyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    // -----------------------------
    //      ATTRIBUTS DU MOB
    // -----------------------------
    public static DefaultAttributeContainer.Builder createRockyAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)          // comme un golem
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)       // vitesse golem
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0)        // dégâts golem
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)  // très lourd
                .add(EntityAttributes.GENERIC_ARMOR, 10.0);               // tanky
    }

    // -----------------------------
    //      IA DU GOLEM
    // -----------------------------
    @Override
    protected void initGoals() {
        // Attaque au corps à corps
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));

        // Se balade
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.6));

        // Regarde autour
        this.goalSelector.add(3, new LookAroundGoal(this));

        // Regarde les joueurs
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));

        // Défend et attaque les mobs hostiles
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, HostileEntity.class, true));
    }

    // -----------------------------
    //      ATTAQUE DU GOLEM
    // -----------------------------
    @Override
    public boolean tryAttack(Entity target) {
        boolean success = super.tryAttack(target);

        if (success) {
            // dégâts
            target.damage(this.getDamageSources().mobAttack(this),
                    (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));

            // petit knock-up comme le golem de fer
            target.setVelocity(target.getVelocity().add(0, 0.4, 0));
        }

        return success;
    }

    // -----------------------------
    //      ANIMATIONS GECKOLIB
    // -----------------------------
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "controller", 0, state -> {

            if (state.isMoving()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation.rocky.walk"));
            }

            return state.setAndContinue(RawAnimation.begin().thenLoop("animation.rocky.idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}