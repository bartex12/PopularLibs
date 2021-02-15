package com.bartex.states.view.fragments.help

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.presenter.HelpPresenter
import com.bartex.states.view.fragments.BackButtonListener
import kotlinx.android.synthetic.main.fragment_help.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class HelpFragment: MvpAppCompatFragment(),
    IHelpView,
    BackButtonListener {

    companion object {
        fun newInstance() = HelpFragment()
    }

    val presenter: HelpPresenter by moxyPresenter {
        HelpPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)=
        View.inflate(context, R.layout.fragment_help, null)

    override fun setText(text:String?) {
        text?. let{
            tv_help.setText(Html.fromHtml(it))
        }  ?:  getString(R.string.noHelp)
    }

    override fun backPressed(): Boolean = presenter.backPressed()
}