package org.beasts.beastsouls.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import org.beasts.beastsouls.entity.RockyEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RockyRenderer extends GeoEntityRenderer<RockyEntity> {

    public RockyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RockyModel());
        this.shadowRadius = 0.7f; // ombre du mob
    }
}
