package uk.teamsmp.aetherium.commands

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.bukkit.Sound
import org.bukkit.Bukkit.getWorld
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import uk.teamsmp.aetherium.Aetherium
import java.io.File
import kotlin.text.startsWith

data class SrviConfig (
    val teleportPosition: List<Double>,
    val teleportWorld: String,
)

class SrviCommand(private val plugin: Aetherium) : CommandExecutor, TabCompleter {
    private val gson = Gson()
    private val srviConfigFile: File by lazy { File(plugin.dataFolder, "srvi.config.json") }
    private var srviConfig: SrviConfig
    private val mm = plugin.mm

    init {
        srviConfig = loadConfig() // Load config when the command is initialized
    }

    private fun loadConfig(): SrviConfig {
        if (srviConfigFile.exists()) {
            val reader = srviConfigFile.bufferedReader()
            val type = object : TypeToken<SrviConfig>() {}.type
            val data = gson.fromJson<SrviConfig>(reader, type) ?: SrviConfig(listOf(0.5, 0.5, 0.5), "srvibuild")
            reader.close()
            return data
        }
        return SrviConfig(listOf(0.5, 0.5, 0.5), "srvibuild")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        srviConfig = loadConfig()
        if (sender !is Player) {
            sender.sendMessage("Hey console user. Only players can use this command.")
            return true
        }

        val player: Player = sender

        if (!player.hasPermission("aetherium.srvi")) {
            player.sendMessage(mm.deserialize("${plugin.chatPrefix}You do not have permission to use <gold>/srvi</gold>!"))
            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        if (args.isEmpty()) {
            player.sendMessage(mm.deserialize("${plugin.chatPrefix}Incorrect usage!<br>  <gold>/srvi <<yellow>function</yellow>>"))
            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
            return true
        }

        when (val srviFunction = args[0]) {
            "tp" -> {
                player.teleport(
                    Location(
                        getWorld(srviConfig.teleportWorld),
                        srviConfig.teleportPosition[0],
                        srviConfig.teleportPosition[1],
                        srviConfig.teleportPosition[2]
                    )
                )
                player.sendMessage(mm.deserialize("${plugin.chatPrefix}You have been teleported to <gold>Survival Island</gold>."))
                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
                return true
            }
            else -> {
                player.sendMessage(mm.deserialize("${plugin.chatPrefix}The function <yellow>${srviFunction}</yellow> is not valid."))
                player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
                return true
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String>? {
        if (args.size == 1) {
            return listOf("tp").filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}