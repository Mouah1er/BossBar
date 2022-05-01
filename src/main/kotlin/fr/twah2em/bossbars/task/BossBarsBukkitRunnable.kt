package fr.twah2em.bossbars.task

import fr.twah2em.bossbars.bossBars
import fr.twah2em.bossbars.witherLocation
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

            entityWither.location = witherLocation(player.location)
            entityWither.displayName = bossBar.message
            entityWither.health = bossBar.progress * entityWither.maxHealth

            entityWither.dataWatcher.set(0) { (1 shl 5).toByte() }
            entityWither.dataWatcher.set(17) { 0 }
            entityWither.dataWatcher.set(18) { 0 }
            entityWither.dataWatcher.set(19) { 0 }
            entityWither.dataWatcher.set(20) { 1000 }
        }
    }
}