package uk.teamsmp.aetherium.events

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.meta.ItemMeta
import uk.teamsmp.aetherium.Aetherium

class OverrideCustomCrafts(private val plugin: Aetherium) : Listener {
    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val customKey = NamespacedKey(plugin, "aeth_custom")

        event.inventory.matrix.forEach { item ->
            if (item != null && item.type != Material.AIR) {
                val meta: ItemMeta? = item.itemMeta
                if (meta?.persistentDataContainer?.has(customKey) == true) {
                    event.inventory.result = null
                    return
                }
            }
        }
    }
}