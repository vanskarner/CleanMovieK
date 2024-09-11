package com.vanskarner.cleanmoviek.ui.upcoming

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

    var onClick: (item: MovieBasicModel) -> Unit = {}
    var list: MutableList<MovieBasicModel> = ArrayList()

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

    fun addItems(newItems: List<MovieBasicModel>) {
        val startPosition = list.size
        list.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

}

internal class UpcomingViewHolder(val binding: UpcomingItemBinding) : ViewHolder(binding.root) {
    fun bindView(item: MovieBasicModel, onClick: (item: MovieBasicModel) -> Unit) {
        binding.movie = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}