package fr.twah2em.bossbars.engine

import fr.twah2em.mcreflection.sendPacket
import net.minecraft.server.v1_8_R3.EntityWither
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.entity.Player

class BossBar(
    val receiver: Player,
    val entityWither: EntityWither,
    message: String,
    var progress: Float
) {
    var message: String = message
        set(value) {
            field = value
            entityWither.health = progress * entityWither.maxHealth
            sendPacket(receiver, PacketPlayOutEntityMetadata(entityWither.id, entityWither.getDataWatcher(), true))
        }

    init {
        sendPacket(receiver, PacketPlayOutSpawnEntityLiving(entityWither))

        val witherLocation = witherLocation(receiver.location)
        entityWither.setLocation(witherLocation.x, witherLocation.y, witherLocation.z, 0.0f, 0.0f)
        sendPacket(receiver, PacketPlayOutEntityTeleport(entityWither))

        entityWither.health = progress * entityWither.maxHealth
        entityWither.customName = message
        entityWither.customNameVisible = true
        entityWither.isInvisible = true

        sendPacket(receiver, PacketPlayOutEntityMetadata(entityWither.id, entityWither.dataWatcher, true))
    }
}