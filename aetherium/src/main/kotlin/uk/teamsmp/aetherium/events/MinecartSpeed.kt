package uk.teamsmp.aetherium.events

import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.entity.Minecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
// import uk.teamsmp.aetherium.Aetherium

class MinecartSpeed() : Listener {
    @EventHandler
    fun onMinecartMove(event: EntityMoveEvent) {
        val entity = event.entity
        if (entity is Minecart) {
            (entity as Minecart).maxSpeed = 30.0
            entity.velocity = entity.velocity.multiply(5.2)
            entity.velocity.multiply(5.2)
        }
    }
}