package org.beasts.beastsouls.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;
import org.beasts.beastsouls.entity.CubeEntity;
import org.beasts.beastsouls.entity.RockyEntity;

public class ModEntities{

    /*
     * Registers our Cube Entity under the ID "entitytesting:cube".
     *
     * The entity is registered under the SpawnGroup#CREATURE category, which is what most animals and passive/neutral mobs use.
     * It has a hitbox size of .75x.75, or 12 "pixels" wide (3/4ths of a block).
     */
    public static final EntityType<CubeEntity> CUBE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Beastsouls.MOD_ID, "cube"),
            EntityType.Builder.create(CubeEntity::new, SpawnGroup.CREATURE).setDimensions(0.75f, 0.75f).build("cube"));
    public static final EntityType<RockyEntity> ROCKY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("beastsouls", "rocky"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RockyEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)) // taille du mob
                    .build()
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(CUBE, CubeEntity.createMobAttributes());

    }
}
