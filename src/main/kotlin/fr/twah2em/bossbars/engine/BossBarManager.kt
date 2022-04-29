package fr.twah2em.bossbars.engine

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import fr.twah2em.bossbars.Main
import fr.twah2em.bossbars.task.BossBarsBukkitRunnable
import fr.twah2em.mcreflection.sendPacket
import net.minecraft.server.v1_8_R3.EntityWither
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.entity.Player

val bossBars: BiMap<Player, BossBar> = HashBiMap.create()

fun createBar(player: Player, title: String, health: Float): BossBar {
    val bar = BossBar(player, EntityWither((player.world as CraftWorld).handle), title, health)
    bossBars[player] = bar

    return bar
}

fun removeBar(player: Player) {
    val bar = bossBars.remove(player)

    if (bar?.entityWither == null) return

    sendPacket(player, PacketPlayOutEntityDestroy(bar.entityWither.id))
}

fun bossBar(player: Player): BossBar? {
    return bossBars[player]
}

fun player(bar: BossBar): Player {
    return bossBars.inverse()[bar]!!
}

internal fun startBossBarsBukkitRunnable() {
    BossBarsBukkitRunnable().runTaskTimerAsynchronously(Main.instance, 0, 1)
}

fun witherLocation(playerLocation: Location): Location {
    return playerLocation.add(playerLocation.direction.multiply(64))
}