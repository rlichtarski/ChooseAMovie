package com.rradzzio.chooseamovie.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rradzzio.chooseamovie.databinding.ItemAddMovieBinding
import com.rradzzio.chooseamovie.domain.model.Movie
import javax.inject.Inject

class AddMovieListAdapter @Inject constructor(
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
        return AddMovieViewHolder(
            ItemAddMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is AddMovieViewHolder -> {
                holder.bind(differ.currentList[position], onItemClickListener)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemFabClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    var movieItems: List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class AddMovieViewHolder(
        val binding: ItemAddMovieBinding,
        private val requestManager: RequestManager,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie, onItemClickListener: ((Movie) -> Unit)?) = with(itemView) {
            binding.apply {

                fabAddMovie.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(item)
                    }
                    fabAddMovie.isEnabled = false
                }

                requestManager
                    .load(item.poster)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(poster)

                movieTitle.text = item.title
                movieYear.text = item.year

            }
        }
    }

}