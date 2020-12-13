package com.jijith.alexa.hmi.displaycard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jijith.alexa.R
import com.jijith.alexa.base.BaseFragment
import com.jijith.alexa.utils.DISPLAY_CARD
import com.jijith.alexa.vo.WikiDisplayCard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_wiki.*


/**
 * A simple [Fragment] subclass.
 * Use the [WikiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WikiFragment : BaseFragment() {
    private var wikiDisplayCard: WikiDisplayCard? = null

    init {
        setName(WikiFragment::class.java.simpleName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wikiDisplayCard = it.getParcelable<WikiDisplayCard>(DISPLAY_CARD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wiki, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param wikiDisplayCard Parameter 1.
         * @return A new instance of fragment WikiFragment.
         */
        @JvmStatic
        fun newInstance(wikiDisplayCard: WikiDisplayCard) =
            WikiFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DISPLAY_CARD, wikiDisplayCard)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvWikiTitle.text = wikiDisplayCard?.title?.mainTitle
        tvWikiDescription.text = wikiDisplayCard?.textField
        Picasso.get()
            .load(wikiDisplayCard?.image?.sources?.get(0)?.url)
            .into(ivWikiImage)
    }
}