package com.example.popularlibs_homrworks.view.adapters.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.model.glide.IImageLoader
import com.example.popularlibs_homrworks.presenters.users.IUserListPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*


class UsersRVAdapter(val presenter: IUserListPresenter, val imageLoader: IImageLoader<ImageView>)
    : RecyclerView.Adapter<UsersRVAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false )
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

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer,
        UserItemView {

        override var pos = -1

        override fun setLogin(text: String) {
            containerView.tv_login.text = text
        }

        override fun loadAvatar(url: String) {
           imageLoader.loadInto(url, containerView.iv_avatar)
        }

    }
}