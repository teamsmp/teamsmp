package uk.teamsmp.aetherium.events

import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import uk.teamsmp.aetherium.Aetherium

class OverrideCustomCrafts(private val plugin: Aetherium) : Listener {

    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val matrix = event.inventory.matrix
        val customKey = NamespacedKey(plugin, "aeth_custom")
        var enableCustomCrafts: Boolean = false

        // Check for custom items in the crafting matrix
        for (item in matrix) {
            if (item != null && isCustom(item, customKey)) {
               enableCustomCrafts = true
                break
            }
        }

        if (enableCustomCrafts) {
            val recipe = event.recipe as? Keyed
            val recipeKey = recipe?.key

            if (recipeKey == null || recipeKey.namespace != "aetherium") {
                // Block crafting if the recipe is invalid or not in the "aetherium" namespace
                event.inventory.result = null
                plugin.logger.info("Blocked crafting with custom items for invalid recipe.")
            } else {
                plugin.logger.info("Allowed crafting for aetherium recipe: ${recipeKey.key}")
            }
        }
    }

    private fun isCustom(item: ItemStack, customKey: NamespacedKey): Boolean {
        val meta = item.itemMeta ?: return false
        return meta.persistentDataContainer.keys.contains(NamespacedKey(plugin, "aeth_custom"))
    }
}
