package com.vanskarner.cleanmoviek.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vanskarner.cleanmoviek.databinding.FavoriteItemBinding
import com.vanskarner.cleanmoviek.ui.MovieBasicModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
internal class FavoritesAdapter @Inject constructor() : RecyclerView.Adapter<FavoritesViewHolder>() {

    var onClick: (item: MovieBasicModel) -> Unit = {}
    private var list: MutableList<MovieBasicModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoriteItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
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

internal class FavoritesViewHolder(val binding: FavoriteItemBinding) : ViewHolder(binding.root) {
    fun bindView(item: MovieBasicModel, onClick: (item: MovieBasicModel) -> Unit) {
        binding.movie = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}