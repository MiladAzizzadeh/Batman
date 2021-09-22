package ir.maziz.batman.feature.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.maziz.batman.R
import ir.maziz.batman.common.implementSpringAnimationTrait
import ir.maziz.batman.data.Search
import ir.maziz.batman.services.image.ImageLoadingService
import ir.maziz.batman.view.BatmanImageView
import org.koin.java.KoinJavaComponent.inject

class MainAdapter(
    val imageLoadingService: ImageLoadingService,
    val movies: List<Search>
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieIv = itemView.findViewById<BatmanImageView>(R.id.movieIv)
        val titleTv = itemView.findViewById<TextView>(R.id.movieTitleTv)
        fun bind(movie: Search) {
            imageLoadingService.load(movieIv, movie.Poster)
            titleTv.text = movie.Title
            itemView.setOnClickListener {

            }
            itemView.implementSpringAnimationTrait()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    override fun getItemCount(): Int = movies.size
}