package com.example.popularlibs_homrworks.presenter

import com.example.popularlibs_homrworks.model.CountersModel

class MainPresenterImpl(val view:MainView, val model: CountersModel): MainPresenter  {
    var value:Int =0

    companion object{
        val COUNTER_1_INDEX = 0
        val COUNTER_2_INDEX = 1
        val COUNTER_3_INDEX = 2
    }

    override fun counterClick1() {
        value = model.getNext(COUNTER_1_INDEX)
        view.setButtonText1(value.toString())
    }

    override fun counterClick2() {
        value = model.getNext(COUNTER_2_INDEX)
        view.setButtonText2(value.toString())
    }

    override fun counterClick3() {
        value = model.getNext(COUNTER_3_INDEX)
        view.setButtonText3( value.toString())
    }
}