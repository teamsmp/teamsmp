package uk.teamsmp.aetherium.events

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import uk.teamsmp.aetherium.Aetherium

class SrviPebbleDrop(private val plugin: Aetherium) : Listener {
    private val mm = plugin.mm

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val player = event.player
        val world = block.world

        if (world.name != "srvi") return // check if on Survival Island

        val stoneMaterials = setOf(
            Material.STONE, Material.STONE_BRICKS, Material.STONE_BRICK_WALL, Material.STONE_BRICK_SLAB, Material.STONE_BRICK_STAIRS,
            Material.STONE_SLAB, Material.STONE_STAIRS, Material.CHISELED_STONE_BRICKS
        )
        if (block.type !in stoneMaterials) return // check if stone is mined

        val itemInHand = player.inventory.itemInMainHand // find item held by player
        if (itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)) return // check if tool has silk touch

        event.isDropItems = false // stop default drop

        val pebbles = plugin.itemUtils.createCustomItem(
            Material.FLINT, listOf("aeth_custom", "pebble"),
            (1..3).random(),
            mm.deserialize("Pebble"),
            listOf(
                mm.deserialize("<red><b><i>!<reset> <grey>Use 4 in a crafting grid<reset>")
            )
        ) // create 1-3 custom pebbles
        repeat(pebbles.amount) {
            world.dropItemNaturally(block.location, pebbles) // drop pebbles
        }
    }
}
