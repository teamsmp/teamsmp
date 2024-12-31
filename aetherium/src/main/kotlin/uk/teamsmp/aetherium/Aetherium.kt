package uk.teamsmp.aetherium

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import uk.teamsmp.aetherium.commands.*
import uk.teamsmp.aetherium.events.*
import uk.teamsmp.aetherium.recipes.StoneFromPebbleRecipe
import uk.teamsmp.aetherium.utils.ItemUtils
import uk.teamsmp.aetherium.utils.Utils

class Aetherium : JavaPlugin() {
    val mm = MiniMessage.miniMessage()
    val chatPrefix = "<gradient:#8922bd:blue:aqua><b>${Utils.smallCaps("aetherium")}</b></gradient> <grey>> <reset>"
    val itemUtils = ItemUtils(this@Aetherium)

    override fun onEnable() {
        getCommand("modvote")?.apply {
            setExecutor(ModVoteCommand(this@Aetherium))
            tabCompleter = ModVoteCommand(this@Aetherium)
        }
        logger.info("Command MODVOTE has been registered.")
        getCommand("srvi")?.apply {
            setExecutor(SrviCommand(this@Aetherium))
            tabCompleter = SrviCommand(this@Aetherium)
        }
        logger.info("Command SRVI has been registered.")
        getCommand("cgive")?.apply {
            setExecutor(CustomGiveCommand(this@Aetherium))
            tabCompleter = CustomGiveCommand(this@Aetherium)
        }
        logger.info("Command CGIVE has been registered.")
        getCommand("punish")?.apply {
            setExecutor(PunishCommand(this@Aetherium))
            tabCompleter = PunishCommand(this@Aetherium)
        }

        server.pluginManager.registerEvents(SrviPebbleDrop(this@Aetherium), this)
        logger.info("Event listener SRVI_PEBBLE has been registered.")
        server.pluginManager.registerEvents(OverrideCustomCrafts(this@Aetherium), this)
        logger.info("Event listener OVERRIDE_CUSTOM_CRAFTS has been registered.")
        server.pluginManager.registerEvents(MinecartSpeed(), this)
        logger.info("Event listener MINECART_SPEED has been registered.")

        StoneFromPebbleRecipe(this@Aetherium).registerRecipe()
        logger.info("Recipe STONE_FROM_PEBBLE has been registered.")

        logger.info("aetherium has loaded")
        logger.info("Hold onto your pickaxes! aetherium is now live!")
    }

    override fun onDisable() {
        logger.info("aetherium left the chat.\nStatus: don't wake me up unless you have snacks")
        logger.info("aetherium has been disabled")
    }
}
