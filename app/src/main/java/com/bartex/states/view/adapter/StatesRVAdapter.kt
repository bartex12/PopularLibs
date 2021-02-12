package com.bartex.states.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bartex.states.R
import com.bartex.states.presenter.states.IStateListPresenter
import com.bartex.states.view.adapter.imageloader.IImageLoader
import com.bartex.states.view.main.TAG
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_state.view.*

class StatesRVAdapter(val presenter: IStateListPresenter, val imageLoader: IImageLoader<ImageView>)
    : RecyclerView.Adapter<StatesRVAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state, parent, false )
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
        StatesItemView {

        override var pos = -1

        override fun setName(name: String) {
            containerView.tv_name.text = name
           // Log.d(TAG, "StatesRVAdapter ViewHolder setLogin name =$name")
        }

        override fun loadFlag(url: String) {
            //Log.d(TAG, "StatesRVAdapter ViewHolder loadFlag url =$url")
            imageLoader.loadInto(url, containerView.iv_flag)
        }
    }
}