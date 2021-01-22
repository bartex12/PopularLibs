package com.example.popularlibs_homrworks.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.GlideImageLoader
import com.example.popularlibs_homrworks.model.api.ApiHolder
import com.example.popularlibs_homrworks.model.repository.RetrofitGithubUsersRepo
import com.example.popularlibs_homrworks.presenter.UsersPresenter
import com.example.popularlibs_homrworks.view.adapter.UsersRVAdapter
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UsersFragment : MvpAppCompatFragment(),    UsersView,    BackButtonListener {

    companion object { fun newInstance() = UsersFragment()}

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepo(ApiHolder.api), App.instance.router) }

    var adapter: UsersRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter( presenter.usersListPresenter, GlideImageLoader() )
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
        Log.d(TAG, "UsersFragment updateList ")
    }

    override fun backPressed() = presenter.backPressed()

}