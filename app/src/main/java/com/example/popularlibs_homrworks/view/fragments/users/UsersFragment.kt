package com.example.popularlibs_homrworks.view.fragments.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.glide.GlideImageLoader
import com.example.popularlibs_homrworks.presenters.users.UsersPresenter
import com.example.popularlibs_homrworks.view.adapters.users.UsersRVAdapter
import com.example.popularlibs_homrworks.view.fragments.BackButtonListener
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(),
    UsersView,
    BackButtonListener {

    companion object {
        //инжектим фрагмент в методе newInstance при его вызове - если нужно что-то
        //инжектить внутри фрагмента - но этого не должно быть
        fun newInstance() =UsersFragment()
    }

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)
        adapter =
            UsersRVAdapter(
                presenter.usersListPresenter,
                GlideImageLoader().apply {
                    App.instance.appComponent.inject(this)
                }
            )
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
        Log.d(TAG, "UsersFragment updateList ")
    }

    override fun backPressed() = presenter.backPressed()

}