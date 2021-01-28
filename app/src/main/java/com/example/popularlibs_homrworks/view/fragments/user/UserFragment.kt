package com.example.popularlibs_homrworks.view.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.api.ApiHolder
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.network.AndroidNetworkStatus
import com.example.popularlibs_homrworks.model.repositories.userrepo.RetrofitGithubRepositoriesRepo
import com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos.RoomRepositoriesRepoCash
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.presenters.user.UserRepoPresenter
import com.example.popularlibs_homrworks.view.adapters.user.UserRepoAdapter
import com.example.popularlibs_homrworks.view.fragments.BackButtonListener
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment(val user: GithubUser) : MvpAppCompatFragment(),
    UserView,    BackButtonListener {

    var adapter: UserRepoAdapter? = null

    val repoPresenter: UserRepoPresenter by moxyPresenter {
        UserRepoPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubRepositoriesRepo(
                ApiHolder.api,
                AndroidNetworkStatus(
                    App.instance
                ),
                Database.getInstance(), RoomRepositoriesRepoCash()
            ),
            App.instance.router,
            user
        )
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
