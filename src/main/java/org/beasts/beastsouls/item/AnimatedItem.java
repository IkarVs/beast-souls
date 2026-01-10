package org.beasts.beastsouls.item;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.beasts.beastsouls.client.item.AnimatedItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AnimatedItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache =
            new SingletonAnimatableInstanceCache(this);

    // ⚠️ FOURNI PAR GeckoLib — PAS par toi
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public AnimatedItem(Settings settings) {
        super(settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final AnimatedItemRenderer renderer =
                    new AnimatedItemRenderer();

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(
                this,
                "controller",
                0,
                state -> {
                    state.setAndContinue(
                            RawAnimation.begin().thenLoop("idle")
                    );
                    return PlayState.CONTINUE;
                }
        ));
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // direction du regard
            Vec3d look = user.getRotationVec(1.0F);

            // création de la boule de feu
            SmallFireballEntity fireball = new SmallFireballEntity(
                    world,
                    user,
                    look.x,
                    look.y,
                    look.z
            );

            // position : devant la tête du joueur
            fireball.setPosition(
                    user.getX() + look.x * 1.5,
                    user.getEyeY() - 0.1,
                    user.getZ() + look.z * 1.5
            );

            world.spawnEntity(fireball);
        }

        // animation bras + cooldown
        user.swingHand(hand);
        user.getItemCooldownManager().set(this, 20); // 1 seconde

        return TypedActionResult.success(stack, world.isClient);
    }

}
