package me.lojosho.hibiscuscommons.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityIdRegistry {

    private static final ConcurrentHashMap<Integer, UUID> ENTITY_TO_UUID = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Integer> UUID_TO_ENTITY = new ConcurrentHashMap<>();

    private EntityIdRegistry() {
    }

    public static void updatePlayer(Player player) {
        if (player == null) {
            return;
        }
        int entityId = player.getEntityId();
        UUID uuid = player.getUniqueId();
        Integer previousId = UUID_TO_ENTITY.put(uuid, entityId);
        if (previousId != null && previousId != entityId) {
            ENTITY_TO_UUID.remove(previousId);
        }
        ENTITY_TO_UUID.put(entityId, uuid);
    }

    public static void removePlayer(UUID uuid) {
        if (uuid == null) {
            return;
        }
        Integer entityId = UUID_TO_ENTITY.remove(uuid);
        if (entityId != null) {
            ENTITY_TO_UUID.remove(entityId);
        }
    }

    public static @Nullable UUID getUuid(int entityId) {
        return ENTITY_TO_UUID.get(entityId);
    }
}
