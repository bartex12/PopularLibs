package com.example.popularlibs_homrworks.view.adapters.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.presenters.user.IUserRepoListPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo_user.view.*

class UserRepoAdapter(val presenter: IUserRepoListPresenter)
    : RecyclerView.Adapter<UserRepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context)
         .inflate(R.layout.item_repo_user,parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos =position
        presenter.bindView(holder)

        holder.containerView.setOnClickListener{
            //нельзя иначе осуществить вызов nullable-значения функционального типа.
            //вызовет itemClickListener, если он не равен null
            presenter.itemClickListener?.invoke(holder)
        }
    }

    inner class ViewHolder(override val containerView: View)
        :RecyclerView.ViewHolder(containerView), LayoutContainer,
        UserRepoItemView {

        override var pos: Int = -1

        override fun setId(text: String) {
            containerView.tv_id.text =text
        }

        override fun setName(text: String) {
            containerView.tv_name.text =text
        }

        override fun setForks(forks: Int) {
            containerView.tv_forks.text =forks.toString()
        }
    }
}