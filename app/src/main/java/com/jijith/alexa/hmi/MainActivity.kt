package com.jijith.alexa.hmi

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jijith.alexa.R
import com.jijith.alexa.databinding.ActivityMainBinding
import com.jijith.alexa.hmi.registration.RegistrationFragment
import com.jijith.alexa.hmi.splash.SplashFragment
import com.jijith.alexa.lib.IMyAlexaServiceInterface
import com.jijith.alexa.service.managersimpl.AlexaEngineManagerImpl
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 0

    private val requiredPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private lateinit var binding: ActivityMainBinding


    lateinit var alexaEngineManager: AlexaEngineManagerImpl

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    this@MainActivity,
                    MainRepository(this@MainActivity)
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        // Check if permissions are missing and must be requested
        val requests = ArrayList<String>()

        for (permission in requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                === PackageManager.PERMISSION_DENIED
            ) {
                requests.add(permission)
            }
        }

        // Request necessary permissions if not already granted, else start app
        if (requests.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                requests.toTypedArray(), PERMISSION_REQUEST_CODE
            )
        } else {
            supportFragmentManager.beginTransaction().add(R.id.flContainer, SplashFragment())
                .disallowAddToBackStack()
                .commitAllowingStateLoss()
            Handler().postDelayed({
                supportFragmentManager.beginTransaction().replace(R.id.flContainer, RegistrationFragment())
                    .addToBackStack(RegistrationFragment::javaClass.name)
                    .commitAllowingStateLoss()
            }, 5000)
        }
//        alexaEngineManager = AlexaEngineManagerImpl(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        // Permission request was denied
                        Toast.makeText(
                            this, "Permissions required",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                // Permissions have been granted. Start app
                supportFragmentManager.beginTransaction().add(R.id.flContainer, SplashFragment())
                    .disallowAddToBackStack()
                    .commitAllowingStateLoss()
                Handler().postDelayed({
                    supportFragmentManager.beginTransaction().replace(R.id.flContainer, RegistrationFragment())
                        .addToBackStack(RegistrationFragment::javaClass.name)
                        .commitAllowingStateLoss()
                }, 5000)
            } else {
                // Permission request was denied
                Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStop() {
        viewModel.stopBinding()
        super.onStop()
    }
}