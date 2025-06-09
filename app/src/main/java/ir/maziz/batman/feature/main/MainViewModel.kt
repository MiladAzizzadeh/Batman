package ir.maziz.batman.feature.main

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.maziz.batman.common.BatmanSingleObserver
import ir.maziz.batman.common.BatmanViewModel
import ir.maziz.batman.common.apiKey
import ir.maziz.batman.common.asyncNetworkRequest
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.Search
import ir.maziz.batman.data.repo.BatmanMoviesRepository
import android.util.Log

const val INITIAL_SEARCH_TERM = "batman"
private const val TAG = "MainViewModel"

class MainViewModel(private val batmanMoviesRepository: BatmanMoviesRepository) : BatmanViewModel() {
    val moviesLiveData = MutableLiveData<List<Search>>()

    init {
        progressBarLiveData.value = true
        batmanMoviesRepository.getMovies(apiKey, INITIAL_SEARCH_TERM)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BatmanSingleObserver<BatmanResponse>(compositeDisposable) {
                override fun onSuccess(t: BatmanResponse) {
                    moviesLiveData.value = t.Search
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    Log.e(TAG, "Error fetching initial movies: ", e)
                    // Optionally, you could post an error to another LiveData to show in UI
                }
            })
    }

    fun searchMovies(searchTerm: String) {
        progressBarLiveData.value = true
        batmanMoviesRepository.getMovies(apiKey, searchTerm)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BatmanSingleObserver<BatmanResponse>(compositeDisposable) {
                override fun onSuccess(t: BatmanResponse) {
                    if (t.Response == "True") {
                        moviesLiveData.value = t.Search
                    } else {
                        moviesLiveData.value = emptyList() // Clear list if search returns no results or error
                        Log.w(TAG, "Search returned no results or an error: ${t.Error}")
                        // Optionally, post a message to UI (e.g. using a SingleLiveEvent)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    moviesLiveData.value = emptyList() // Clear list on error
                    Log.e(TAG, "Error searching movies for '$searchTerm': ", e)
                    // Optionally, you could post an error to another LiveData to show in UI
                }
            })
    }
}