import eu.thefuturecloud.api.event.player.CloudPlayerConnectEvent
import eu.thefuturecloud.api.event.player.CloudPlayerDisconnectEvent
import eu.thefuturecloud.api.eventapi.IListener
import eu.thefuturecloud.api.eventapi.annotation.EventHandler

class PlayerEventListener(private val playerServiceManager: PlayerServiceManager) : IListener {

    @EventHandler
    fun onPlayerConnect(event: CloudPlayerConnectEvent) {
        val player = event.cloudPlayer
        val playerName = player.getName()
        val groupName = "Spawn"

        playerServiceManager.handlePlayerJoin(playerName, groupName)
    }

    @EventHandler
    fun onPlayerDisconnect(event: CloudPlayerDisconnectEvent) {
        val player = event.cloudPlayer
        val service = player.getConnectedServer()
        if (service != null) {
            playerServiceManager.handlePlayerLeave(player.getName(), service)
        }
    }
}
