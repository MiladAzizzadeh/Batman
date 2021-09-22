package ir.maziz.batman.feature.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import ir.maziz.batman.R
import ir.maziz.batman.common.BatmanActivity
import ir.maziz.batman.common.id
import ir.maziz.batman.services.image.ImageLoadingService
import ir.maziz.batman.view.BatmanImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.w3c.dom.Text

class DetailActivity : BatmanActivity() {
    val detailViewModel: DetailViewModel by viewModel { parametersOf(intent.extras!!.getString(id)) }
    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }
        detailViewModel.detailBatmanLiveData.observe(this) {
            val moviePoster = findViewById<BatmanImageView>(R.id.moviePoster)
            val movieTitle = findViewById<TextView>(R.id.movieTitle)
            val imdbRatingTv = findViewById<TextView>(R.id.imdbTv)
            val yearTv = findViewById<TextView>(R.id.yearTv)
            val genreTv = findViewById<TextView>(R.id.genreTv)
            val runTimeTv = findViewById<TextView>(R.id.runTimeTv)
            imageLoadingService.load(moviePoster, it.Poster)
            movieTitle.text = it.Title
            imdbRatingTv.text = it.imdbRating
            yearTv.text = it.Year
            genreTv.text = it.Genre
            runTimeTv.text = it.Runtime
        }
        findViewById<ImageView>(R.id.backIv).setOnClickListener {
            finish()
        }
    }
}