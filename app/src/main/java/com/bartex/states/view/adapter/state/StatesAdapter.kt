package com.bartex.states.view.adapter.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bartex.states.R
import com.bartex.states.model.entity.state.State
import com.bartex.states.view.adapter.imageloader.IImageLoader
import kotlinx.android.synthetic.main.item_state.view.*

class StatesAdapter(private val onitemClickListener: OnitemClickListener, val imageLoader: IImageLoader<ImageView>)
    : RecyclerView.Adapter<StatesAdapter.ViewHolder> () {

    interface OnitemClickListener{
        fun onItemclick(state: State)
    }

    //так сделано чтобы передавать список в адаптер без конструктора
    // - через присвоение полю значения
    var listStates: List<State> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
            // Log.d(TAG, "StateRVAdapter set =  ${listStates.map { it.nameRus }}")
            //Log.d(TAG, "StateRVAdapter bind size = ${MapOfState.mapStates.entries.size} }")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listStates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStates[position])
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        fun bind(state: State){
            itemView.tv_name.text = state.name
            state.flag?.let { imageLoader.loadInto(it, itemView.iv_flag) }
            itemView.setOnClickListener {
                onitemClickListener.onItemclick(state)
                //для тестов
                Toast.makeText(itemView.context, itemView.tv_name.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}