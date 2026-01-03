package org.beasts.beastsouls.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import org.beasts.beastsouls.entity.RockyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RockyAttackGoal extends MeleeAttackGoal {

    private final RockyEntity entity;
    private int attackDelay = 10;              // moment où l’animation doit se lancer
    private int ticksUntilNextAttack = 40;     // cooldown total
    private boolean shouldCountTillNextAttack = false;

    private boolean hasPlayedAttackAnimation = false;

    public static final Logger LOGGER =
            LoggerFactory.getLogger("beastsouls");

    public RockyAttackGoal(PathAwareEntity mob, double speed, boolean followEvenIfNotSeen) {
        super(mob, speed, followEvenIfNotSeen);
        this.entity = (RockyEntity) mob;
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 10;
        ticksUntilNextAttack = 40;
        hasPlayedAttackAnimation = false; // reset
    }

    @Override
    protected void attack(LivingEntity enemy, double distToEnemySqr) {

        if (distToEnemySqr <= this.getSquaredMaxAttackDistance(enemy)) {
            shouldCountTillNextAttack = true;

            // 🔥 Lance l’animation UNE SEULE FOIS
            if (!hasPlayedAttackAnimation && isTimeToStartAttackAnimation()) {
                LOGGER.info("lancement animation");
                entity.triggerAnim("attack_controller", "attack");
                hasPlayedAttackAnimation = true; // empêche de relancer
            }

            // 🔥 Applique les dégâts quand le timer atteint 0
            if (isTimeToAttack()) {
                LOGGER.info("MOMENT ATTACK");
                this.mob.getLookControl().lookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
            }

        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            hasPlayedAttackAnimation = false; // reset
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = attackDelay * 4; // 40 ticks
        hasPlayedAttackAnimation = false;            // reset animation
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected void performAttack(LivingEntity enemy) {
        resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        LOGGER.info("JE VAIS TRY ATTACK");
        this.mob.tryAttack(enemy);
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        hasPlayedAttackAnimation = false;
        super.stop();
    }
}