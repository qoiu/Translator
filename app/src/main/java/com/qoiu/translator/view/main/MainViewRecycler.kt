package com.qoiu.translator.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.translator.R
import com.qoiu.translator.data.SearchResults
import kotlinx.android.synthetic.main.word_information.view.*

class MainViewRecycler(private var onListItemClickListener: OnListItemClickListener) : RecyclerView.Adapter<MainViewRecycler.ViewHolder>() {

    private var data : List<SearchResults> = arrayListOf()


    fun setData(data: List<SearchResults>) {
        this.data = data.toMutableList()
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
            itemView.word_description.text=data.meanings?.get(0)?.translation?.translation
            itemView.setOnClickListener{openInNewWindow(data)}
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }


    private fun openInNewWindow(listItemData: SearchResults) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResults)
    }
}