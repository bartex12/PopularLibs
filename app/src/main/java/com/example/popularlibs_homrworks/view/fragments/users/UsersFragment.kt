package com.example.popularlibs_homrworks.view.fragments.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.api.ApiHolder
import com.example.popularlibs_homrworks.model.glide.GlideImageLoader
import com.example.popularlibs_homrworks.model.network.AndroidNetworkStatus
import com.example.popularlibs_homrworks.model.repositories.usersrepo.RetrofitGithubUsersRepo
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers.RoomGithubUsersCache
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.presenters.users.UsersPresenter
import com.example.popularlibs_homrworks.view.adapters.users.UsersRVAdapter
import com.example.popularlibs_homrworks.view.fragments.BackButtonListener
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class UsersFragment : MvpAppCompatFragment(),
    UsersView,
    BackButtonListener {

    // ДЗ Избавиться от инъекции ниже
    @Inject
    lateinit var database: Database

    companion object {
        //инжектим фрагмент в методе newInstance при его вызове
        fun newInstance() =UsersFragment().apply {
            App.instance.appComponent.inject(this)
        }
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            //2
            RetrofitGithubUsersRepo(
                ApiHolder.api,
                AndroidNetworkStatus( App.instance),
                RoomGithubUsersCache(database)
            )
        ).apply {
            App.instance.appComponent.inject(this)
        }
    }

    var adapter: UsersRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)
        adapter =
            UsersRVAdapter(
                presenter.usersListPresenter,
                GlideImageLoader()
            )
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
        Log.d(TAG, "UsersFragment updateList ")
    }

    override fun backPressed() = presenter.backPressed()

}