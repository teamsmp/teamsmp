package uk.teamsmp.aetherium.recipes

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import uk.teamsmp.aetherium.Aetherium

class StoneFromPebbleRecipe(private val plugin: Aetherium) {
    private val mm = plugin.mm
    fun registerRecipe() {
        val pebble = plugin.itemUtils.createCustomItem(
            Material.FLINT, listOf("srvi_custom", "pebble"),
            1,
            mm.deserialize("Pebble"),
            listOf(
                mm.deserialize("<red><b><i>!<reset> <grey>Use 4 in a crafting grid<reset>")
            )
        )

        val cobblestone = ItemStack(Material.COBBLESTONE, 2)

        val shapedRecipe = ShapedRecipe(NamespacedKey(plugin, "stone_from_pebble"), cobblestone)

        shapedRecipe.shape("PP", "PP")
        shapedRecipe.setIngredient('P', pebble)

        plugin.server.addRecipe(shapedRecipe)
    }
}