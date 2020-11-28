package com.jijith.alexa.hmi.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.jijith.alexa.R
import com.jijith.alexa.base.BaseFragment
import com.jijith.alexa.hmi.MainViewModel
import com.jijith.alexa.hmi.splash.SplashFragment
import kotlinx.android.synthetic.main.fragment_registration.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : BaseFragment() {

    private val sharedMainViewModel: MainViewModel by activityViewModels()

    init {
        super.setName(RegistrationFragment::class.java.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        btnStart.setOnClickListener{
            sharedMainViewModel.startCBL()
        }
    }
}
