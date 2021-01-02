package com.example.popularlibs_homrworks

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.presenter.MainPresenter
import com.example.popularlibs_homrworks.view.UsersRVAdapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(),
    MainView {

    private val presenter by moxyPresenter {
        MainPresenter( GithubUsersRepo())
    }
    var adapter: UsersRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(this)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }
}