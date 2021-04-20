package com.rradzzio.chooseamovie.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.rradzzio.chooseamovie.databinding.ItemMovieBinding
import com.rradzzio.chooseamovie.domain.model.Movie
import javax.inject.Inject

class MovieListAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return (
                    oldItem.title == newItem.title
                            && oldItem.year == newItem.year
                            && oldItem.poster == newItem.poster
                    )
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (oldItem == newItem)

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    var movieItems: List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val requestManager: RequestManager,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) = with(itemView) {
            binding.apply {

                requestManager
                    .load(item.poster)
                    .transition(withCrossFade())
                    .into(ivMoviePoster)

                tvTitle.text = item.title
                tvYear.text = item.year
            }
        }
    }

}