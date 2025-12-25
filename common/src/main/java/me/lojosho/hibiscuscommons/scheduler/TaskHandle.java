package me.lojosho.hibiscuscommons.scheduler;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TaskHandle {

    TaskHandle NONE = new TaskHandle() {
        @Override
        public boolean cancel() {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return true;
        }
    };

    boolean cancel();

    boolean isCancelled();

    static @NotNull TaskHandle from(@Nullable ScheduledTask task) {
        if (task == null) {
            return NONE;
        }
        return new ScheduledTaskHandle(task);
    }
}

final class ScheduledTaskHandle implements TaskHandle {

    private final ScheduledTask task;

    ScheduledTaskHandle(@NotNull ScheduledTask task) {
        this.task = task;
    }

    @Override
    public boolean cancel() {
        return task.cancel() != ScheduledTask.CancelledState.CANCELLED_ALREADY;
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }
}
