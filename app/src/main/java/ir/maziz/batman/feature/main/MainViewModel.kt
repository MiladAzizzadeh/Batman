package ir.maziz.batman.feature.main

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.maziz.batman.common.BatmanSingleObserver
import ir.maziz.batman.common.BatmanViewModel
import ir.maziz.batman.common.asyncNetworkRequest
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.Search
import ir.maziz.batman.data.repo.BatmanMoviesRepository

const val apiKey = "3e974fca"
const val search = "batman"

class MainViewModel(batmanMoviesRepository: BatmanMoviesRepository) : BatmanViewModel() {
    val moviesLiveData = MutableLiveData<List<Search>>()

    init {
        progressBarLiveData.value = true
        batmanMoviesRepository.getMovies(apiKey, search)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BatmanSingleObserver<BatmanResponse>(compositeDisposable) {
                override fun onSuccess(t: BatmanResponse) {
                    moviesLiveData.value = t.Search
                }
            })
    }
}