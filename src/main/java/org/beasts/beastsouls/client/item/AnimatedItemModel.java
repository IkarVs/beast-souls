package org.beasts.beastsouls.client.item;

import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;
import org.beasts.beastsouls.item.AnimatedItem;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedItemModel extends GeoModel<AnimatedItem> {
    @Override
    public Identifier getModelResource(AnimatedItem animatedItem) {
        return new Identifier(Beastsouls.MOD_ID,"geo/animated_item.geo.json");
    }

    @Override
    public Identifier getTextureResource(AnimatedItem animatedItem) {
        return new Identifier(Beastsouls.MOD_ID,"textures/item/animated_item.png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedItem animatedItem) {
        return new Identifier(Beastsouls.MOD_ID,"animations/animated_item.animation.json");
    }
}
