package me.lojosho.hibiscuscommons.scheduler;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class HibiscusScheduler {

    private final Plugin plugin;

    public HibiscusScheduler(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull TaskHandle runGlobal(@NotNull Runnable run) {
        return TaskHandle.from(globalScheduler().run(plugin, task -> run.run()));
    }

    public @NotNull TaskHandle runGlobalLater(@NotNull Runnable run, long delayTicks) {
        return TaskHandle.from(globalScheduler().runDelayed(plugin, task -> run.run(), delayTicks));
    }

    public @NotNull TaskHandle runGlobalAtFixedRate(@NotNull Runnable run, long initialDelayTicks, long periodTicks) {
        return TaskHandle.from(globalScheduler().runAtFixedRate(plugin, task -> run.run(), normalizeInitialDelay(initialDelayTicks), periodTicks));
    }

    public void executeGlobal(@NotNull Runnable run) {
        globalScheduler().execute(plugin, run);
    }

    public @NotNull TaskHandle runAsync(@NotNull Runnable run) {
        return TaskHandle.from(asyncScheduler().runNow(plugin, task -> run.run()));
    }

    public @NotNull TaskHandle runAsyncLater(@NotNull Runnable run, long delay, @NotNull TimeUnit unit) {
        return TaskHandle.from(asyncScheduler().runDelayed(plugin, task -> run.run(), delay, unit));
    }

    public @NotNull TaskHandle runAsyncAtFixedRate(@NotNull Runnable run, long initialDelay, long period, @NotNull TimeUnit unit) {
        return TaskHandle.from(asyncScheduler().runAtFixedRate(plugin, task -> run.run(), initialDelay, period, unit));
    }

    public @NotNull TaskHandle runAtLocation(@NotNull Location location, @NotNull Runnable run) {
        return TaskHandle.from(regionScheduler().run(plugin, location, task -> run.run()));
    }

    public @NotNull TaskHandle runAtLocationLater(@NotNull Location location, @NotNull Runnable run, long delayTicks) {
        return TaskHandle.from(regionScheduler().runDelayed(plugin, location, task -> run.run(), delayTicks));
    }

    public @NotNull TaskHandle runAtLocationAtFixedRate(@NotNull Location location, @NotNull Runnable run, long initialDelayTicks, long periodTicks) {
        return TaskHandle.from(regionScheduler().runAtFixedRate(plugin, location, task -> run.run(), normalizeInitialDelay(initialDelayTicks), periodTicks));
    }

    public void executeAtLocation(@NotNull Location location, @NotNull Runnable run) {
        regionScheduler().execute(plugin, location, run);
    }

    public @NotNull TaskHandle runAtEntity(@NotNull Entity entity, @NotNull Runnable run) {
        return TaskHandle.from(entity.getScheduler().run(plugin, task -> run.run(), null));
    }

    public @NotNull TaskHandle runAtEntityLater(@NotNull Entity entity, @NotNull Runnable run, long delayTicks) {
        return TaskHandle.from(entity.getScheduler().runDelayed(plugin, task -> run.run(), null, delayTicks));
    }

    public @NotNull TaskHandle runAtEntityAtFixedRate(@NotNull Entity entity, @NotNull Runnable run, long initialDelayTicks, long periodTicks) {
        return TaskHandle.from(entity.getScheduler().runAtFixedRate(plugin, task -> run.run(), null, normalizeInitialDelay(initialDelayTicks), periodTicks));
    }

    private long normalizeInitialDelay(long initialDelayTicks) {
        return Math.max(1L, initialDelayTicks);
    }

    private @NotNull GlobalRegionScheduler globalScheduler() {
        return Bukkit.getServer().getGlobalRegionScheduler();
    }

    private @NotNull RegionScheduler regionScheduler() {
        return Bukkit.getServer().getRegionScheduler();
    }

    private @NotNull AsyncScheduler asyncScheduler() {
        return Bukkit.getServer().getAsyncScheduler();
    }
}
