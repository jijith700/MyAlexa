package com.jijith.alexa.hmi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jijith.alexa.R
import com.jijith.alexa.databinding.ActivityMainBinding
import com.jijith.alexa.hmi.home.HomeFragment
import com.jijith.alexa.hmi.registration.RegistrationFragment
import com.jijith.alexa.hmi.splash.SplashFragment
import com.jijith.alexa.service.managersimpl.AlexaEngineManagerImpl
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
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, SplashFragment(), SplashFragment::class.java.simpleName)
                .addToBackStack(SplashFragment::class.java.simpleName)
                .commit()
        }

        viewModel.loadRegistration.observe(this, androidx.lifecycle.Observer {
            Timber.d("backstack %d", supportFragmentManager.backStackEntryCount)
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.flContainer,
                    RegistrationFragment(),
                    RegistrationFragment::class.java.simpleName
                )
                .addToBackStack(RegistrationFragment::javaClass.name)
                .commitAllowingStateLoss()
        })

        viewModel.loadHome.observe(this, androidx.lifecycle.Observer {
            Timber.d("backstack %d", supportFragmentManager.backStackEntryCount)
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, HomeFragment(), HomeFragment::class.java.simpleName)
                .addToBackStack(HomeFragment::javaClass.name)
                .commitAllowingStateLoss()
        })
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
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, SplashFragment())
                    .commit()
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

    override fun onBackPressed() {
        Timber.d("onBackPressed %d", supportFragmentManager.backStackEntryCount)
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        }
        super.onBackPressed()
    }
}
