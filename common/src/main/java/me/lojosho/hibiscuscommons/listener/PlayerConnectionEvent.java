package me.lojosho.hibiscuscommons.listener;

import me.lojosho.hibiscuscommons.nms.NMSHandlers;
import me.lojosho.hibiscuscommons.util.EntityIdRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerConnectionEvent implements Listener {

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EntityIdRegistry.updatePlayer(player);
        NMSHandlers.getHandler().getUtilHandler().handleChannelOpen(player);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        EntityIdRegistry.removePlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getScheduler().run(
            me.lojosho.hibiscuscommons.HibiscusCommonsPlugin.getInstance(),
            task -> EntityIdRegistry.updatePlayer(player),
            null
        );
    }
}
