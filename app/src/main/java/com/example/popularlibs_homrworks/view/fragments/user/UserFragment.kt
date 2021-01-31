package com.example.popularlibs_homrworks.view.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.presenters.user.UserRepoPresenter
import com.example.popularlibs_homrworks.view.adapters.user.UserRepoAdapter
import com.example.popularlibs_homrworks.view.fragments.BackButtonListener
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment() : MvpAppCompatFragment(),
    UserView,    BackButtonListener {

    companion object {
        const val USER_ARG = "user"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
        }
    }

    var adapter: UserRepoAdapter? = null

    val repoPresenter: UserRepoPresenter by moxyPresenter {
        val user = arguments?.getParcelable<GithubUser>(USER_ARG) as GithubUser
        UserRepoPresenter(user).apply {
            App.instance.appComponent.inject(this)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    //реализация метода BackButtonListener
    override fun backPressed(): Boolean {
        Log.d(TAG, "UserFragment backPressed")
        repoPresenter.backPressed()
        return true
    }


    override fun init() {
        rv_user.layoutManager = LinearLayoutManager(context)
        adapter = UserRepoAdapter(repoPresenter.userListPresenter )
        rv_user.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }


}
