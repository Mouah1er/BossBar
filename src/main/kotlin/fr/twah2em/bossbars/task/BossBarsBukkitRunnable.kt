package fr.twah2em.bossbars.task

import fr.twah2em.bossbars.engine.bossBars
import fr.twah2em.bossbars.engine.witherLocation
import fr.twah2em.mcreflection.sendPacket
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

class BossBarsBukkitRunnable : BukkitRunnable() {

    override fun run() {
        bossBars()
    }

    private fun bossBars() {
        bossBars.forEach {
            val player = it.key
            val bossBar = it.value

            val entityWither = bossBar.entityWither

            val witherLocation: Location = witherLocation(player.location)
            entityWither.setLocation(
                witherLocation.x, witherLocation.y, witherLocation.z,
                witherLocation.yaw, witherLocation.pitch
            )
            sendPacket(player, PacketPlayOutEntityTeleport(entityWither))

            entityWither.customName = bossBar.message
            entityWither.customNameVisible = true
            entityWither.isInvisible = true

            entityWither.dataWatcher.watch(17, 0)
            entityWither.dataWatcher.watch(18, 0)
            entityWither.dataWatcher.watch(19, 0)
            entityWither.dataWatcher.watch(20, 1000)
            entityWither.dataWatcher.watch(0, (1 shl 5).toByte())

            sendPacket(
                player,
                PacketPlayOutEntityMetadata(entityWither.id, entityWither.dataWatcher, true)
            )
        }
    }
}