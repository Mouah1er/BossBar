package fr.twah2em.bossbars

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import fr.twah2em.bossbars.task.BossBarsBukkitRunnable
import fr.twah2em.mcreflection.getPacket
import fr.twah2em.mcreflection.sendPacket
import org.bukkit.Location
import org.bukkit.entity.Player

val bossBars: BiMap<Player, BossBar> = HashBiMap.create()

fun createBar(player: Player, title: String, health: Double): BossBar {
    val bar = BossBar(player, title, health)
    bossBars[player] = bar

    return bar
}

fun removeBar(player: Player) {
    val bar = bossBars.remove(player)

    if (bar?.entityWither?.nmsEntityWither == null) return

    sendPacket(player, getPacket("PacketPlayOutEntityDestroy", bar.entityWither.id))
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