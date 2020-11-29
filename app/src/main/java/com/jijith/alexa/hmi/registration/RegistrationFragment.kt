package com.jijith.alexa.hmi.registration

import android.os.Bundle
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
import com.jijith.alexa.databinding.FragmentRegistrationBinding
import com.jijith.alexa.hmi.MainViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : BaseFragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val sharedMainViewModel: MainViewModel by activityViewModels()

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: RegistrationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RegistrationViewModel(
                    context,
                    RegistrationRepository(context), sharedMainViewModel
                ) as T
            }
        }
    }

    init {
        super.setName(RegistrationFragment::class.java.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        btnStart.setOnClickListener {
            sharedMainViewModel.startCBL()
        }

        sharedMainViewModel.url.observe(viewLifecycleOwner, Observer {
            binding.tvUrl.text = String.format(getString(R.string.tv_txt_url), it)
            binding.tvUrl.visibility = View.VISIBLE
        })

        sharedMainViewModel.code.observe(viewLifecycleOwner, Observer {
            binding.tvCode.text = it
        })

        viewModel.isSignedIn.observe(viewLifecycleOwner, Observer {
            Timber.d("sign in: %s", it)
            if (it) {
                sharedMainViewModel.loadHome()
            }
        })

        viewModel.isSigned()
    }

    override fun onDestroy() {
        sharedMainViewModel.stopCBL()
        super.onDestroy()
    }
}
