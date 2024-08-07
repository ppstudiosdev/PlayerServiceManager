import eu.thefuturecloud.api.CloudAPI
import eu.thefuturecloud.api.external.ICloudModule
import eu.thefuturecloud.api.external.ICloudModuleInfo

class PlayerServiceModule : ICloudModule {

    override fun onEnable(moduleInfo: ICloudModuleInfo) {
        val playerServiceManager = PlayerServiceManager()
        val eventManager = CloudAPI.instance.getEventManager()
        eventManager.registerListener(this, PlayerEventListener(playerServiceManager))
    }

    override fun onDisable() {

    }
}
