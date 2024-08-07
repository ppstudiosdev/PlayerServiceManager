import eu.thefuturecloud.api.CloudAPI
import eu.thefuturecloud.api.service.ICloudService
import eu.thefuturecloud.api.service.ServiceState
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class PlayerServiceManager {

    private val cloudAPI = CloudAPI.instance
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private val serviceTimers = mutableMapOf<String, Runnable>()

    fun handlePlayerJoin(playerName: String, groupName: String) {
        val playerServer = cloudAPI.getCloudPlayerManager().getCachedCloudPlayer(playerName)?.getConnectedServer()
        if (playerServer != null && playerServer.getServiceGroup().getName() == groupName) {
            val targetGroup = "YourName"

            val existingService = cloudAPI.getCloudServiceManager().getCloudServiceByName(playerName)
            if (existingService != null) {
                existingService.setState(ServiceState.VISIBLE)
                cancelInvisibleTimer(existingService)
            } else {
                val serviceConfiguration = cloudAPI.getCloudServiceConfiguration()
                val newService = cloudAPI.getCloudServiceManager().createCloudService(serviceConfiguration
                    .setName(playerName)
                    .setServiceGroup(cloudAPI.getCloudServiceGroupManager().getServiceGroupByName(targetGroup)!!)
                )
                newService.start()
            }
        }
    }

    fun handlePlayerLeave(playerName: String, service: ICloudService) {
        if (service.getConnectedPlayers().isEmpty()) {
            scheduleInvisibleTimer(service)
        }
    }

    private fun scheduleInvisibleTimer(service: ICloudService) {
        val task = Runnable {
            service.setState(ServiceState.INVISIBLE)
        }
        serviceTimers[service.getName()] = task
        scheduler.schedule(task, 30, TimeUnit.SECONDS)
    }

    private fun cancelInvisibleTimer(service: ICloudService) {
        val task = serviceTimers.remove(service.getName())
        task?.let { scheduler.remove(it) }
    }
}
