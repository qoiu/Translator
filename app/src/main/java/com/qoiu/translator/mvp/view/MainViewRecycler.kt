package com.qoiu.translator.mvp.view

import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.translator.R
import com.qoiu.translator.mvp.model.data.SearchResults
import kotlinx.android.synthetic.main.word_information.view.*

class MainViewRecycler(private var data: List<SearchResults>) : RecyclerView.Adapter<MainViewRecycler.ViewHolder>() {

    fun setData(data: List<SearchResults>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_information, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: SearchResults) {
            itemView.word_title.text=data.text
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

}