package org.beasts.beastsouls.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;
import org.beasts.beastsouls.entity.CubeEntity;
import org.beasts.beastsouls.client.BeastsoulsClient;


/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CubeEntityRenderer extends MobEntityRenderer<CubeEntity, CubeEntityModel> {

    public CubeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CubeEntityModel(context.getPart(BeastsoulsClient.MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(CubeEntity entity) {
        return new Identifier(Beastsouls.MOD_ID, "textures/entity/cube/cube.png");
    }
}
