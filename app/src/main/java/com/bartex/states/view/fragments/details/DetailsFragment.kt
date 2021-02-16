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
        DetailsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            state = it.getParcelable<State>(
                ARG_STATE
            )
        }
        Log.d(TAG, "DetailsFragment onCreate state = ${state}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_show_weater.setOnClickListener {
            state?. let {presenter.btnCapitalClick(it)}
        }

        btn_show_geo.setOnClickListener {
            state?. let {sendGeoIntent(it)}
            //state?. let {presenter.btnGeoClick(it)} // не работает в андроид 9
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
                //imageLoader.loadIntoSimple(it, iv_flag_big )
            }
        }
    }

    override fun setStateArea() {
        state?. let {
            if(it.area != 0f ){
                it.area?. let{
                    if (it>1000000f){
                        val area = (it)/1000000
                        tv_state_area.text =
                            String.format("Площадь: %.1f млн. кв. км.", area)
                    }else if (it in 1000f..1000000f){
                        val area = (it)/1000
                        tv_state_area.text =
                            String.format("Площадь: %.1f тыс. кв. км.", area)
                    }else{
                        tv_state_area.text =
                            String.format("Площадь: %.1f  кв. км.", it)
                    }
                }?: let{tv_state_area.text ="Площадь территории неизвестна"}
            }
        }
    }

    override fun setStatePopulation() {
        state?. let {
            if (it.population!=0){
                it.population?. let{
                    if(it>1000000){
                        val population = (it.toFloat())/1000000
                        tv_state_population.text =
                            String.format("Население: %.1f млн. чел.", population)
                    }else if (it in 1000..1000000){
                        val population = (it.toFloat())/1000
                        tv_state_population.text =
                            String.format("Население: %.1f тыс. чел.", population)

                    }else{
                        tv_state_population.text =
                            String.format("Население: %s чел.", it)
                    }
                }?: let{tv_state_area.text ="Население: численность неизвестна"}
            }
        }
    }

    override fun setStateCapital() {
        state?. let {
            if (it.capital != ""){
                tv_state_capital.text =  String.format("Столица:   %S ", it.capital)
            }else{
                presenter.btnEnabled()
                tv_state_capital.text =  "Название столицы неизвестно"
            }
        }
    }

    override fun setBtnCapitalEnabled() {
        tv_state_capital.isEnabled =true
    }

    fun sendGeoIntent(state: State?) {
        var zoom = 0
        state?.area?. let{
            if (it<10f){
                zoom = 13
            }else if (it in 10f..1000f){
                zoom = 11
            }else if (it in 1000f..30000f){
                zoom = 9
            }else if (it in 1000f..100000f) {
                zoom = 7
            }else if (it in 100000f..1000000f){
                zoom = 5
            }else if (it in 1000000f..5000000f){
                zoom = 3
            }else{
                zoom = 1
            }
        }
        val geoCoord = String.format("geo:%s,%s?z=%s",
            state?.latlng?.get(0).toString(), state?.latlng?.get(1).toString(), zoom.toString())

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoCoord))
        // You could specify package for use the GoogleMaps app, only
        val packageManager: PackageManager = requireActivity().packageManager
        if (isPackageInstalled("com.google.android.apps.maps", packageManager)) {
            intent.setPackage("com.google.android.apps.maps")
        }
        requireActivity().startActivity(intent)
    }

    private fun isPackageInstalled(packageName: String,packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}