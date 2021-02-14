package com.bartex.states.view.fragments.details

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
            state?. let { presenter.btnCapitalClick(it)}

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

}