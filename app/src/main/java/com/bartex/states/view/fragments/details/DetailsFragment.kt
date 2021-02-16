package com.bartex.states.view.fragments.details

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.utils.StateUtils
import com.bartex.states.presenter.DetailsPresenter
import com.bartex.states.view.fragments.BackButtonListener
import com.bartex.states.view.main.TAG
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.fragment_details.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class DetailsFragment : MvpAppCompatFragment(),
    IDetailsView,
    BackButtonListener {

    companion object {
        private const val ARG_STATE = "state"

        @JvmStatic
        fun newInstance(state: State) =
            DetailsFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_STATE, state)
                    }
                }
    }
    private var state: State? = null

    val presenter: DetailsPresenter by moxyPresenter {
        //здесь аргументы нужны - иначе state = null
        arguments?.let {state = it.getParcelable<State>(ARG_STATE )}
        Log.d(TAG, "DetailsFragment onCreate state = ${state}")
        DetailsPresenter(state).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        //здесь аргументы нужны для корректной обработки поворота экрана
        arguments?.let {state = it.getParcelable<State>(ARG_STATE )}

        btn_show_weater.setOnClickListener {
            state?. let {presenter.btnCapitalClick(it)}
        }

        btn_show_geo.setOnClickListener {
            state?. let {presenter.sendGeoIntent(it)}
        }
    }

    //реализация метода BackButtonListener
    override fun backPressed(): Boolean = presenter.backPressed()

    override fun setStateName() {
        state?. let { tv_state_name.text = it.name}
    }

    override fun setStateRegion() {
        state?. let { tv_state_region.text =
            String.format("Регион:   %S ", it.region)}
    }

    override fun setStateFlag() {
        state?. let {
            it.flag?. let{
                GlideToVectorYou.justLoadImage(requireActivity(), Uri.parse(it), iv_flag_big)
            }
        }
    }

    override fun setStateArea(area:String) {
        tv_state_area.text = area
    }

    override fun setStatePopulation(population:String) {
        tv_state_population.text = population
    }

    override fun setStateCapital(capital:String) {
        tv_state_capital.text = capital
    }

    override fun sendGeoIntent(geoCoord:String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoCoord))
        // пакет для использования Гугл карты
        val packageManager: PackageManager = requireActivity().packageManager
        if (isPackageInstalled("com.google.android.apps.maps", packageManager)) {
            intent.setPackage("com.google.android.apps.maps")
        }
        requireActivity().startActivity(intent)
    }

     //если ошибка - возвращаем false
    private fun isPackageInstalled(packageName: String,packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}