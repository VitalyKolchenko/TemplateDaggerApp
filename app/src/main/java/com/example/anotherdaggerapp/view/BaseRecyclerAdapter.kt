package com.example.anotherdaggerapp.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class BaseRecyclerAdapter<T>(
    private val vhFactory: (ViewGroup) -> BaseViewHolder<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return vhFactory(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder<T>).bind(items[position])
    }

    fun replaceItems(items: Collection<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        this.items.clear()
        notifyDataSetChanged()
    }
}