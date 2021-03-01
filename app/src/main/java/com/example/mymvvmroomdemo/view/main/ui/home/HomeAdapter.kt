package com.example.mymvvmroomdemo.view.main.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymvvmroomdemo.R
import com.mindorks.example.paging3.data.response.Data
import kotlinx.android.synthetic.main.row_home.view.*


class HomeAdapter :
    PagingDataAdapter<Data, HomeAdapter.ViewHolder>(DataDifferntiator) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.row_home, parent, false)
        )
    }


    object DataDifferntiator : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtName.text =
            "${getItem(position)?.firstName} ${getItem(position)?.lastName}"
        holder.itemView.txtEmail.text = getItem(position)?.email

        Glide.with(holder.itemView).load(getItem(position)?.avatar).into(holder.itemView.imageView)

    }

}