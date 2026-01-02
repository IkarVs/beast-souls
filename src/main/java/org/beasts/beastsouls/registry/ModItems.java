package org.beasts.beastsouls.registry;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;

import java.util.function.Supplier;

public class ModItems {

    public static <T extends Item> T register(String name, Supplier<T> supplier) {
        T item = supplier.get();
        Registry.register(
                Registries.ITEM,
                new Identifier(Beastsouls.MOD_ID, name),
                item
        );
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
//        ça n'a aucun sens ce que j'écris là puisse que je register soul gem alors que bon register n'est pas supposé appelé celle ci
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(ModItems.SOUL_GEM));
        return item;
    }
//    L'ID DE L'ITEM NE PEUT PAS AVOIR DE MAJ !!
    public static final Item SOUL_GEM = register(
            "soul_gem",
            () -> new Item(new Item.Settings())
    );

    public static void init() {}
}
