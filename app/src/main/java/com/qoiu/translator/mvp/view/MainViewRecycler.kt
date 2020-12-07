package com.qoiu.translator.mvp.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.translator.R
import com.qoiu.translator.mvp.model.data.SearchResults
import com.squareup.picasso.Picasso
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
            itemView.word_description.text=data.meanings?.get(0)?.translation?.translation
            val url ="https:"+Uri.parse(data.meanings?.get(0)?.imageUrl)
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_baseline_picture_in_picture_24)
                .into(itemView.word_image)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

}