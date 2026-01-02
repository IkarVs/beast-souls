package org.beasts.beastsouls;

import net.fabricmc.api.ModInitializer;
import org.beasts.beastsouls.registry.ModEntities;
import org.beasts.beastsouls.registry.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Beastsouls implements ModInitializer {
    public static final Logger LOGGER =
            LoggerFactory.getLogger("beastsouls");
    public static final String MOD_ID = "beastsouls";


    @Override
    public void onInitialize() {
            LOGGER.info("TESSST YAHOOOOOOO");
            ModItems.init();
            ModEntities.init();
    }
}
