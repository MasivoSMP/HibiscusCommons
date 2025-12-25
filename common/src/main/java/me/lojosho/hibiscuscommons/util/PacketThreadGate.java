package me.lojosho.hibiscuscommons.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PacketThreadGate {

    private PacketThreadGate() {
    }

    public static void runOnEntity(@NotNull Plugin plugin, @Nullable Entity entity, @NotNull Runnable run) {
        if (entity == null) {
            return;
        }
        entity.getScheduler().run(plugin, task -> run.run(), null);
    }

    public static void runOnEntityLater(@NotNull Plugin plugin, @Nullable Entity entity, @NotNull Runnable run, long delayTicks) {
        if (entity == null) {
            return;
        }
        entity.getScheduler().runDelayed(plugin, task -> run.run(), null, delayTicks);
    }

    public static void runOnLocation(@NotNull Plugin plugin, @Nullable Location location, @NotNull Runnable run) {
        if (location == null) {
            return;
        }
        plugin.getServer().getRegionScheduler().run(plugin, location, task -> run.run());
    }
}
