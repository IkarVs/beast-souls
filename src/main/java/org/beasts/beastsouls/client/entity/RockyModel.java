package org.beasts.beastsouls.client.entity;

import net.minecraft.util.Identifier;
import org.beasts.beastsouls.entity.RockyEntity;
import software.bernie.geckolib.model.GeoModel;

public class RockyModel extends GeoModel<RockyEntity> {

    @Override
    public Identifier getModelResource(RockyEntity animatable) {
        return new Identifier("beastsouls", "geo/rocky.geo.json");
    }

    @Override
    public Identifier getTextureResource(RockyEntity animatable) {
        return new Identifier("beastsouls", "textures/entity/rocky.png");
    }

    @Override
    public Identifier getAnimationResource(RockyEntity animatable) {
        return new Identifier("beastsouls", "animations/rocky.animation.json");
    }
}
