package me.lojosho.hibiscuscommons.nms.v1_21_R4;

import me.lojosho.hibiscuscommons.HibiscusCommonsPlugin;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSCommon {

    public void sendPacket(Player player, Packet packet) {
        if (Bukkit.isOwnedByCurrentRegion(player)) {
            sendPacketNow(player, packet);
            return;
        }
        player.getScheduler().run(HibiscusCommonsPlugin.getInstance(), task -> sendPacketNow(player, packet), null);
    }

    private void sendPacketNow(Player player, Packet packet) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        ServerPlayerConnection connection = serverPlayer.connection;
        connection.send(packet);
    }

}

