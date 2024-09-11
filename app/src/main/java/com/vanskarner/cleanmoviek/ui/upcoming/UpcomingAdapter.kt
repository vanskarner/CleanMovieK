package com.vanskarner.cleanmoviek.ui.upcoming

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vanskarner.cleanmoviek.databinding.UpcomingItemBinding
import com.vanskarner.cleanmoviek.ui.MovieBasicModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class UpcomingAdapter @Inject constructor() : RecyclerView.Adapter<UpcomingViewHolder>() {

    private var onClick: (item: MovieBasicModel) -> Unit = {}
    private var list: MutableList<MovieBasicModel> = ArrayList()

    fun setOnClickListener(listener: (item: MovieBasicModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val binding = UpcomingItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieBasicModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}

internal class UpcomingViewHolder(val binding: UpcomingItemBinding) : ViewHolder(binding.root) {
    fun bindView(item: MovieBasicModel, onClick: (item: MovieBasicModel) -> Unit) {
        binding.movie = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}