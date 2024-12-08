package uk.teamsmp.aetherium.events

import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.meta.ItemMeta
import uk.teamsmp.aetherium.Aetherium

class OverrideCustomCrafts(private val plugin: Aetherium) : Listener {
    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val customKey = NamespacedKey(plugin, "aeth_custom")

        val recipe: Recipe? = event.recipe
        val recipeKey = if (recipe is Keyed) recipe.key else null
        val isAetheriumRecipe = recipeKey?.namespace == "aetherium"

        // Check if the recipe's namespace is not "aetherium"
        if (!isAetheriumRecipe) {
            // Check all items in the crafting grid
            event.inventory.matrix.forEach { item ->
                if (item != null && item.type != Material.AIR) {
                    val meta: ItemMeta? = item.itemMeta
                    if (meta?.persistentDataContainer?.has(customKey) == true) {
                        // If any item has the custom data and recipe isn't allowed, cancel the crafting result
                        event.inventory.result = null
                        return
                    }
                }
            }

        }
    }
}