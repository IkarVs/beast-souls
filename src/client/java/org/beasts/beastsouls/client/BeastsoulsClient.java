package org.beasts.beastsouls.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;
import org.beasts.beastsouls.client.entity.CubeEntityModel;
import org.beasts.beastsouls.client.entity.CubeEntityRenderer;
import org.beasts.beastsouls.client.entity.RockyRenderer;
import org.beasts.beastsouls.registry.ModEntities;

public class BeastsoulsClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_CUBE_LAYER =
            new EntityModelLayer(new Identifier(Beastsouls.MOD_ID, "cube"), "main");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, CubeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CUBE, CubeEntityRenderer::new);
        // avec gecko pas besoin de mettre de layer
        EntityRendererRegistry.register(ModEntities.ROCKY, RockyRenderer::new);


    }
}
