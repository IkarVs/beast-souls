package org.beasts.beastsouls.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class PlayerEvents {

    private PlayerEvents() {} // pas d'instance

    // jumping est protected enfaite, il faut faire un mixin

//    public static void register() {
//        ServerTickEvents.END_SERVER_TICK.register(server -> {
//            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
//                player.
//                if (player.isOnGround() && player.jumping ) {
//                    player.giveItemStack(new ItemStack(Items.DIAMOND));
//                }
//            }
//        });
//    }
}
