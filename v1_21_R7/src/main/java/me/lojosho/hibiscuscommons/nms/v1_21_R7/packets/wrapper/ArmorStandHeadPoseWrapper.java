package me.lojosho.hibiscuscommons.nms.v1_21_R7.packets.wrapper;

import me.lojosho.hibiscuscommons.packets.PacketType;
import me.lojosho.hibiscuscommons.packets.wrapper.PacketWrapper;
import net.minecraft.core.Rotations;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.List;

public class ArmorStandHeadPoseWrapper implements PacketWrapper {

    private final int entityId;
    private final float xDegrees;
    private final float yDegrees;
    private final float zDegrees;

    public ArmorStandHeadPoseWrapper(int entityId, float xDegrees, float yDegrees, float zDegrees) {
        this.entityId = entityId;
        this.xDegrees = xDegrees;
        this.yDegrees = yDegrees;
        this.zDegrees = zDegrees;
    }

    @Override
    public PacketType getType() {
        return PacketType.ARMOR_STAND_HEAD_POSE;
    }

    @Override
    public Object toNativePacket() {
        ArmorStand armorStand = new ArmorStand(net.minecraft.world.entity.EntityType.ARMOR_STAND, MinecraftServer.getServer().overworld());
        armorStand.setId(entityId);
        armorStand.setHeadPose(new Rotations(xDegrees, yDegrees, zDegrees));

        List<SynchedEntityData.DataValue<?>> dataValues = armorStand.getEntityData().packDirty();
        return new ClientboundSetEntityDataPacket(entityId, dataValues);
    }
}
