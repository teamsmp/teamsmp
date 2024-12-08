package uk.teamsmp.aetherium.commands

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import uk.teamsmp.aetherium.Aetherium

enum class PunishmentType {
    WARN, MUTE, TEMPMUTE, KICK, BAN, TEMPBAN
}

data class Punishment (
    val type: PunishmentType,
    val playerName: String,
    val reason: String,
    val time: String?
)

class PunishCommand(private val plugin: Aetherium) : CommandExecutor, TabCompleter {
    private val mm = plugin.mm

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("aetherium.punish")) {
            sender.sendMessage(mm.deserialize("${plugin.chatPrefix}You do not have permission to use <gold>/punish</gold>!"))
            if (sender is Player) sender.playSound(sender.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(mm.deserialize("${plugin.chatPrefix}Incorrect usage!<br>  <gold>/punish <<yellow>type</yellow>> <<yellow>player</yellow>> [<yellow>time</yellow>] <<yellow>reason</yellow>>"))
            if (sender is Player) sender.playSound(sender.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        val playerName: String = args[1]
        val type: PunishmentType = when (val typeInput = args[0]) {
            "warn" -> PunishmentType.WARN
            "mute" -> PunishmentType.MUTE
            "tmute" -> PunishmentType.TEMPMUTE
            "kick" -> PunishmentType.KICK
            "ban" -> PunishmentType.BAN
            "tban" -> PunishmentType.TEMPBAN
            else -> {
                sender.sendMessage(mm.deserialize("${plugin.chatPrefix}<yellow>${typeInput}</yellow> is not a valid punishment type!"))
                if (sender is Player) sender.playSound(sender.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
                return true
            }
        }
        val punishment: Punishment
        if (type == PunishmentType.TEMPBAN || type == PunishmentType.TEMPMUTE) {
            val time = args[2]
            val reason = args.sliceArray(3 until args.size).joinToString(" ")
            punishment = Punishment(type, playerName, reason, time)
            sender.sendMessage(mm.deserialize("${plugin.chatPrefix}Punished <yellow>${playerName}</yellow> for <yellow>${time}</yellow> for <yellow>${reason}</yellow>."))
            if (sender is Player) sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.5f)
            return true
        } else {
            val reason = args.sliceArray(2 until args.size).joinToString(" ")
            punishment = Punishment(type, playerName, reason, null)
            sender.sendMessage(mm.deserialize("${plugin.chatPrefix}Punished <yellow>${playerName}</yellow> for <yellow>${reason}</yellow>."))
            if (sender is Player) sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.5f)
            return true
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String>? {
        if (args.size == 1) {
            return listOf("warn", "mute", "tmute", "kick", "ban", "tban").filter { it.startsWith(args[0], ignoreCase = true) }
        }
        if (args.size == 2) {
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.startsWith(args[1], ignoreCase = true) }
        }
        return emptyList()
    }
}