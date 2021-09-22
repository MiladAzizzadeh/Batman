package ir.maziz.batman.feature.detail

import androidx.lifecycle.MutableLiveData
import ir.maziz.batman.common.BatmanSingleObserver
import ir.maziz.batman.common.BatmanViewModel
import ir.maziz.batman.common.apiKey
import ir.maziz.batman.common.asyncNetworkRequest
import ir.maziz.batman.data.DetailBatmanResponse
import ir.maziz.batman.data.repo.BatmanMoviesRepository

class DetailViewModel(val batmanMoviesRepository: BatmanMoviesRepository, imdbId: String) :
    BatmanViewModel() {
    val detailBatmanLiveData = MutableLiveData<DetailBatmanResponse>()

    init {
        progressBarLiveData.value = true
        batmanMoviesRepository.getDetail(apiKey, imdbId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BatmanSingleObserver<DetailBatmanResponse>(compositeDisposable) {
                override fun onSuccess(t: DetailBatmanResponse) {
                    detailBatmanLiveData.value = t
                }
            })
    }
}