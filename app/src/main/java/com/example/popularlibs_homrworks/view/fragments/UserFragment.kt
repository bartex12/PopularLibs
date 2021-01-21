package com.example.popularlibs_homrworks.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.presenter.UserPresenter
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment(val user:GithubUser) : MvpAppCompatFragment(), UserView , BackButtonListener{

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(App.instance.router, user) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    //реализация метода BackButtonListener
    override fun backPressed(): Boolean {
        presenter.backPressed()
        Log.d(TAG, "UserFragment backPressed")
        return true
    }

    override fun setLogin(login: String) {
            tv_user_login.text = login
        }
    }
