package com.example.popularlibs_homrworks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.CountersModel
import com.example.popularlibs_homrworks.presenter.MainPresenter
import com.example.popularlibs_homrworks.presenter.MainPresenterImpl
import com.example.popularlibs_homrworks.presenter.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    //Создание презентера можно вынести это в класс-инжектор с методом inject
    private val model: CountersModel = CountersModel()
    private val presenter: MainPresenter = MainPresenterImpl(this, model)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_counter1.setOnClickListener {  presenter.counterClick1() }
        btn_counter2.setOnClickListener{  presenter.counterClick2() }
        btn_counter3.setOnClickListener{  presenter.counterClick3() }
    }

    override fun setButtonText1(text: String) {
        btn_counter1.text = text
    }

    override fun setButtonText2(text: String) {
        btn_counter2.text = text
    }

    override fun setButtonText3(text: String) {
        btn_counter3.text = text
    }
}