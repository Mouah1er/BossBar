package fr.twah2em.bossbars

import fr.twah2em.bossbars.entity.EntityWither
import fr.twah2em.mcreflection.getPacket
import fr.twah2em.mcreflection.sendPacket
import org.bukkit.entity.Player

class BossBar(
    receiver: Player,
    message: String,
    progress: Double
) {
    val entityWither = EntityWither(receiver, receiver.world, message, progress * 200.0)

    var message = message
        set(value) {
            field = value
            entityWither.displayName = value
        }

    var progress = progress
        set(value) {
            field = value
            entityWither.health = value * 200.0
        }

    init {
        sendPacket(receiver, getPacket("PacketPlayOutSpawnEntityLiving", entityWither.nmsEntityWither))

        val witherLocation = witherLocation(receiver.location)
        entityWither.location = witherLocation

        entityWither.health = progress * entityWither.maxHealth
        entityWither.displayName = message
        entityWither.isInvisible = true
    }
}