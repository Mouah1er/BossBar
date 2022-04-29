package fr.twah2em.bossbars

import fr.twah2em.bossbars.engine.startBossBarsBukkitRunnable
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var instance: Main
    }

    override fun onEnable() {
        instance = this;
        logger.info("BossBars enabled")

        startBossBarsBukkitRunnable()
    }
}