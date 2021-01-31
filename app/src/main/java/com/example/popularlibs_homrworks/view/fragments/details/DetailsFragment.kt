package com.example.popularlibs_homrworks.view.fragments.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.presenters.details.DetailsPresenter
import com.example.popularlibs_homrworks.view.fragments.BackButtonListener
import com.example.popularlibs_homrworks.view.fragments.user.UserFragment
import com.example.popularlibs_homrworks.view.main.TAG
import kotlinx.android.synthetic.main.fragment_user_repo.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class DetailsFragment()
    : MvpAppCompatFragment(), DetailsView, BackButtonListener {

    companion object {
        private const val REPO_ARG = "repository"

        fun newInstance(userRepos:GithubUserRepos, user: GithubUser) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPO_ARG, userRepos)
                putParcelable(UserFragment.USER_ARG, user)
            }
        }
    }

   val presenter: DetailsPresenter by moxyPresenter {
       val user = arguments?.getParcelable<GithubUser>(UserFragment.USER_ARG) as GithubUser
       DetailsPresenter(user).apply {
           App.instance.appComponent.inject(this)
       }
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_user_repo, container, false)
    }

    override fun setUserForks() {
        val userRepos = arguments?.getParcelable<GithubUserRepos>(REPO_ARG) as GithubUserRepos
        userRepos.forks?. let{
            tv_user_forks.text = it.toString()
        }
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        Log.d(TAG, "UserFragment backPressed")
        return true
    }
}