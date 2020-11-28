package com.jijith.alexa.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment: Fragment() {

    private var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(" %s onCreate",  name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("%s onViewCreated",  name)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        Timber.d("%s onAttach",  name)
        super.onAttach(context)
    }

    override fun onStart() {
        Timber.d("%s onStart",  name)
        super.onStart()
    }

    override fun onResume() {
        Timber.d("%s onResume",  name)
        super.onResume()
    }

    override fun onPause() {
        Timber.d("%s onPause",  name)
        super.onPause()
    }

    override fun onStop() {
        Timber.d("%s onStop" ,  name)
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.d("%s onDestroyView",  name)
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.d("%s onDestroy",  name)
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.d("%s onDetach",  name)
        super.onDetach()
    }

    fun setName(name: String) {
        this.name = name
    }
}