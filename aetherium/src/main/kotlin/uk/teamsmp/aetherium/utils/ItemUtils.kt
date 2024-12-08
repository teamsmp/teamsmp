package uk.teamsmp.aetherium.utils

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import uk.teamsmp.aetherium.Aetherium

class ItemUtils(private val plugin: Aetherium) {
    private val mm = plugin.mm

    fun createCustomItem(
        material: Material,
        customNbt: List<String>,
        amount: Int = 1,
        name: Component = mm.deserialize("Placeholder Item"),
        lore: List<Component> = listOf(),
        hideAttributes: Boolean = true
    ): ItemStack {
        val item = ItemStack(material, amount)
        val meta = item.itemMeta

        if (meta != null) {
            meta.displayName(name) // set custom name
            meta.lore(lore) // set custom lore

            val key = NamespacedKey(plugin, customNbt[0]) // get specified NBT key
            meta.persistentDataContainer.set(
                key, PersistentDataType.STRING, customNbt[1]
            ) // add custom NBT to item

            if (hideAttributes) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES) // hide attributes

            // Apply meta to the item
            item.itemMeta = meta
        }
        return item
    }
}
