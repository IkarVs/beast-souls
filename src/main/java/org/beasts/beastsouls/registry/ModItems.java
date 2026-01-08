package org.beasts.beastsouls.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.beasts.beastsouls.Beastsouls;
import org.beasts.beastsouls.item.AnimatedItem;

import java.util.function.Supplier;

public class ModItems {

    // 🔧 Register générique
    private static <T extends Item> T registerItem(String name, Supplier<T> supplier) {
        T item = supplier.get();
        Registry.register(
                Registries.ITEM,
                new Identifier(Beastsouls.MOD_ID, name),
                item
        );
        return item;
    }

    // 🟣 Items
    public static final Item SOUL_GEM = registerItem(
            "soul_gem",
            () -> new Item(new Item.Settings())
    );

    public static final Item ANIMATED_ITEM = registerItem(
            "animated_item",
            () -> new AnimatedItem(new FabricItemSettings())
    );



    // 📦 Item Groups
    public static void init() {

        // Soul Gem → Ingrédients
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> entries.add(SOUL_GEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(entries -> entries.add(ANIMATED_ITEM));

    }

}
