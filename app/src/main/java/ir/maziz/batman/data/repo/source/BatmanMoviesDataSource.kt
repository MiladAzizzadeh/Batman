package ir.maziz.batman.data.repo.source

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.DetailBatmanResponse

interface BatmanMoviesDataSource {
    fun getMovies(apiKey: String, search: String): Single<BatmanResponse>
    fun getDetail(apiKey: String, imdbId: String): Single<DetailBatmanResponse>
}