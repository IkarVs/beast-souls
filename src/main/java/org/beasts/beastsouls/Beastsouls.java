package org.beasts.beastsouls;

import net.fabricmc.api.ModInitializer;
import org.beasts.beastsouls.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Beastsouls implements ModInitializer {
    public static final Logger LOGGER =
            LoggerFactory.getLogger("examplemod");

    @Override
    public void onInitialize() {
            LOGGER.info("TESSST YAHOOOOOOO");
        ModItems.register();
    }
}
