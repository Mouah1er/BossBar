package fr.twah2em.bossbars.listeners

import fr.twah2em.bossbars.bossBars
import fr.twah2em.bossbars.removeBar
import fr.twah2em.mcreflection.PrimitiveAndWrapper
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (bossBars.containsKey(player)) {
            removeBar(player)
        }
    }
}