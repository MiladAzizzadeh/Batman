package ir.maziz.batman.feature.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.maziz.batman.R
import ir.maziz.batman.common.BatmanActivity
import ir.maziz.batman.common.id
import ir.maziz.batman.feature.detail.DetailActivity
import ir.maziz.batman.services.image.ImageLoadingService
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BatmanActivity(), MainAdapter.ItemEventListener, SearchView.OnQueryTextListener {
    val mainViewModel: MainViewModel by viewModel()
    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        mainViewModel.moviesLiveData.observe(this) {
            val batmanMoviesRv = findViewById<RecyclerView>(R.id.batmanMoviesRv)
            batmanMoviesRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            val adapter = MainAdapter(imageLoadingService, it, this)
            batmanMoviesRv.adapter = adapter
        }

        val categoryDrawables = ArrayList<Drawable>()
        categoryDrawables.add(getDrawable(R.drawable.ic_localmovies)!!)
        categoryDrawables.add(getDrawable(R.drawable.ic_television)!!)
        categoryDrawables.add(getDrawable(R.drawable.ic_gun)!!)
        categoryDrawables.add(getDrawable(R.drawable.ic_hats)!!)
        val categoryTitles = ArrayList<String>()
        categoryTitles.add(getString(R.string.movie))
        categoryTitles.add(getString(R.string.tvShow))
        categoryTitles.add(getString(R.string.action))
        categoryTitles.add(getString(R.string.comedy))
        val categoryRv = findViewById<RecyclerView>(R.id.movieCategoryRv);
        categoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        categoryRv.adapter = CategoryAdapter(categoryDrawables, categoryTitles)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(this)
    }

    override fun onClick(imdbId: String) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(id, imdbId)
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            mainViewModel.searchMovies(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // You can implement search as you type here if needed
        return false
    }
}