package uk.teamsmp.cerium;

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

@Plugin(
    id = "cerium",
    name = "cerium",
    version = BuildConstants.VERSION,
    description = "cerium, the proxy plugin for the Team SMP.",
    url = "teamsmp.pages.dev",
    authors = ["JunglTemple"]
)
class Cerium @Inject constructor(val logger: Logger, server: ProxyServer) {

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
    }
}
