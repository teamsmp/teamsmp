package uk.teamsmp.aetherium.commands

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import uk.teamsmp.aetherium.Aetherium

class CustomGiveCommand(private val plugin: Aetherium): CommandExecutor, TabCompleter {
    private val mm = plugin.mm

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Hey console user. Only players can use this command.")
            return true
        }

        val player: Player = sender

        if (!player.hasPermission("aetherium.cgive")) {
            player.sendMessage(mm.deserialize("${plugin.chatPrefix}You do not have permission to use <gold>/cgive</gold>!"))
            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        if (args.isEmpty()) {
            player.sendMessage(mm.deserialize("${plugin.chatPrefix}Incorrect usage!<br>  <gold>/cgive <<yellow>item</yellow>> [<yellow>amount</yellow>]"))
            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        val amount = if (args.getOrNull(1).isNullOrEmpty()) 1 else args[1].toIntOrNull() ?: 1

        when (val itemToGive = args[0]) {
            "pebble" -> {
                val item = plugin.itemUtils.createCustomItem(
                    Material.FLINT, listOf("aeth_custom", "pebble"),
                    amount,
                    mm.deserialize("Pebble"),
                    listOf(
                        mm.deserialize("<red><b><i>!<reset> <grey>Use 4 in a crafting grid<reset>")
                    )
                )
                player.inventory.addItem(item)
                player.sendMessage(mm.deserialize("${plugin.chatPrefix}You have been given <yellow>${amount} ${itemToGive}${if (amount > 1) "s" else ""}</yellow>."))
                player.playSound(player.location, Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.0f)
                return true
            }
            else -> {
                player.sendMessage(mm.deserialize("${plugin.chatPrefix}The item <yellow>${itemToGive}</yellow> does not exist."))
                player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
                return true
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String?>? {
        if (args.size == 1) {
            return listOf("pebble").filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}