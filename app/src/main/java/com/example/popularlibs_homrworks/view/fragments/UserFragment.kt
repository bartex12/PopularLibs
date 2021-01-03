package com.example.popularlibs_homrworks.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.presenter.UserPresenter
import com.example.popularlibs_homrworks.presenter.UsersPresenter
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


private const val ARG_LOGIN = "login"

class UserFragment : MvpAppCompatFragment(), UserView , BackButtonListener{

    private var login: String? = null

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(App.instance.router) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_LOGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(login: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, login)
                }
            }
    }

    //реализация метода UserView
    override fun init() {
        login?. let {
            tv_user_login.text = it}?:let {tv_user_login.text = ""
        }
    }

    //реализация метода BackButtonListener
    override fun backPressed(): Boolean {
        presenter.backPressed()
        Log.d(TAG, "UserFragment backPressed")
        return true
    }

}