package com.jijith.alexa.hmi.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jijith.alexa.R
import com.jijith.alexa.base.BaseFragment
import com.jijith.alexa.databinding.FragmentSplashBinding
import com.jijith.alexa.hmi.MainViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    private val sharedMainViewModel: MainViewModel by activityViewModels()

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: SplashViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SplashViewModel(
                    context,
                    SplashRepository(context)
                ) as T
            }
        }
    }

    init {
        super.setName(SplashFragment::class.java.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSignedIn.observe(viewLifecycleOwner, Observer {
            if (it) {
                sharedMainViewModel.loadHome()
            } else {
                sharedMainViewModel.loadRegistration()
            }
        })

        Handler().postDelayed({
            viewModel.isSigned()
        }, 5000)
    }
}
