package fr.twah2em.bossbars

import fr.twah2em.bossbars.listeners.PlayerQuitListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var instance: Main
    }

    override fun onEnable() {
        instance = this

        logger.info("BossBars enabled")

        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        startBossBarsBukkitRunnable()
    }
}