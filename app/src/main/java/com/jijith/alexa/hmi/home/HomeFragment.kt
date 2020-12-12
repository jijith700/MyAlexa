package com.jijith.alexa.hmi.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amazon.autovoicechrome.AutoVoiceChromeController
import com.amazon.autovoicechrome.util.AutoVoiceChromeState
import com.jijith.alexa.R
import com.jijith.alexa.base.BaseFragment
import com.jijith.alexa.databinding.FragmentHomeBinding
import com.jijith.alexa.hmi.MainViewModel
import com.jijith.alexa.hmi.displaycard.WikiFragment
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val sharedMainViewModel: MainViewModel by activityViewModels()

    private var autoVoiceChromeController: AutoVoiceChromeController? = null

    private lateinit var animation: Animation

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(
                    context, sharedMainViewModel.mainRepository,
                    HomeRepository(context)
                ) as T
            }
        }
    }

    init {
        super.setName(HomeFragment::class.java.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoVoiceChromeController = AutoVoiceChromeController(context?.applicationContext!!)
        autoVoiceChromeController?.initialize(binding.flVoiceChrome)
        autoVoiceChromeController?.onStateChanged(AutoVoiceChromeState.LISTENING)

        animateTextToCenter()
        animateVoiceChromeToCenter()

        binding.tvWelcome.setOnClickListener({
            Timber.d("Clicked...")
            animateTextToTop()
            animateVoiceChromeToBottom()
        })

        viewModel.wikiDisplayCard.observe(viewLifecycleOwner, Observer {
            animateTextToTop()
            animateVoiceChromeToBottom()
            val wikiDisplayCard = WikiFragment.newInstance(it)
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.flContainer, wikiDisplayCard, WikiFragment::class.java.simpleName)
                ?.addToBackStack(WikiFragment::javaClass.name)
                ?.commitAllowingStateLoss()
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        autoVoiceChromeController?.onDestroy()
    }

    fun animateTextToCenter() {
        Handler().postDelayed({
            Timber.d("width %s", binding.root.width)
            Timber.d("height %s", binding.root.height)
            val parentCenterX = binding.root.x + binding.root.width / 2
            val parentCenterY = binding.root.y + binding.root.height / 2

            Timber.d("parentCenterX %s", parentCenterX)
            Timber.d("X %s", parentCenterX - binding.tvWelcome.width / 2)
            Timber.d("parentCenterY %s", parentCenterY)
            Timber.d("Y %s", parentCenterY - binding.tvWelcome.height / 2)

            binding.tvWelcome.animate()
                .translationX(parentCenterX - binding.tvWelcome.width / 2)
                .translationY(parentCenterY - binding.tvWelcome.height)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(1000)
        }, 1000)
    }

    fun animateVoiceChromeToCenter() {
        Handler().postDelayed({
            val parentCenterX = binding.root.x + binding.root.width / 2
            val parentCenterY = binding.root.y + binding.root.height / 2

            Timber.d("parentCenterX %s", parentCenterX)
            Timber.d("X %s", parentCenterX - binding.flVoiceChrome.width / 2)
            Timber.d("parentCenterY %s", parentCenterY)
            Timber.d("Y %s", -parentCenterY + binding.flVoiceChrome.height * 3)

            binding.flVoiceChrome.animate()
                .translationX(parentCenterX - binding.flVoiceChrome.width / 2)
                .translationY(-parentCenterY + binding.flVoiceChrome.height * 3)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(1000)
        }, 1000)
    }

    fun animateTextToTop() {
        Handler().postDelayed({
            binding.tvWelcome.animate()
                .translationX(10F)
                .translationY(10F)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(1000)
        }, 1000)
    }

    fun animateVoiceChromeToBottom() {
        Handler().postDelayed({
            binding.flVoiceChrome.animate()
                .translationX(0F)
                .translationY(0F)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(1000)
        }, 1000)
    }
}
