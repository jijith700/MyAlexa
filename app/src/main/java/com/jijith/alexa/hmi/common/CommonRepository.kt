package com.jijith.alexa.hmi.common

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import com.jijith.alexa.R
import com.jijith.alexa.hmi.MainRepository
import com.jijith.alexa.hmi.interfaces.Repository
import com.jijith.alexa.lib.IMyAlexaCallbackInterface
import com.jijith.alexa.lib.IMyAlexaServiceInterface
import timber.log.Timber

object CommonRepository : IMyAlexaCallbackInterface {

    private lateinit var iMyAlexaServiceInterface: IMyAlexaServiceInterface
    lateinit var serviceConnection: ServiceConnection
    lateinit var context: Context
    lateinit var mainRepository: MainRepository
    private var repository: HashSet<Repository>? = null

    init {
        repository = HashSet<Repository>()
    }

    /**
     * Start the alexaService
     *
     */
    public fun startService() {
        val className = "com.jijith.alexa.service.AlexaService"
        val packageName = "com.jijith.alexa"
        val i = Intent(className)
        i.component = ComponentName(packageName, className)
        context.startService(i)
    }

    /**
     * Bind alexaService
     *
     * @param serviceName AIDL service name
     */
    public fun connectService(serviceName: String) {
        Timber.d(serviceName)
        val className = "com.jijith.alexa.service.AlexaService"
        val packageName = "com.jijith.alexa"
        serviceConnection = RemoteServiceConnection(context, serviceName)
        val i = Intent(className)
        i.action = serviceName
        i.component = ComponentName(packageName, className)
        val ret: Boolean =
            context.bindService(i, serviceConnection, Context.BIND_AUTO_CREATE)
        Toast.makeText(context, "BindService  $ret", Toast.LENGTH_LONG).show()
    }
    
    public fun stopBinding() {
        iMyAlexaServiceInterface.unregisterCallback(this)
        context.unbindService(serviceConnection)

    }

    /**
     * Class makes the connection with alexa service
     */
    class RemoteServiceConnection internal constructor(
        var context: Context,
        var serviceName: String
    ) :
        ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName,
            boundService: IBinder
        ) {
            if (serviceName == IMyAlexaServiceInterface::class.java.name) {
                iMyAlexaServiceInterface = IMyAlexaServiceInterface.Stub.asInterface(boundService)
                iMyAlexaServiceInterface.registerCallback(CommonRepository)
            }
            Toast
                .makeText(
                    context,
                    context.getString(R.string.service_connected),
                    Toast.LENGTH_LONG
                )
                .show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Toast.makeText(
                context, context.getString(R.string.service_disconnected),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    public fun registerRepositoryObserver(repository: Repository) {
        synchronized(repository) {
            this.repository?.add(repository)
        }
    }

    public fun removeRepositoryObserver(repository: Repository) {
        synchronized(repository) {
            this.repository?.remove(repository)
        }
    }

    fun startCBL() {
        iMyAlexaServiceInterface.startCBL()
    }

    override fun asBinder(): IBinder {
        return iMyAlexaServiceInterface.asBinder()
    }

    override fun onReceiveCBL(url: String?, code: String?) {
        Timber.d("URL: %s Code: %s", url, code)
        mainRepository.onReceiveCBL(url, code)
    }

    override fun onRenderTemplate(payload: String?) {
        Timber.d("payload: %s", payload)
        for (repo in repository!!) {
            repo.onRenderTemplate(payload)
        }
    }

    fun stopCBL() {
        iMyAlexaServiceInterface.stopCBL()
    }
}