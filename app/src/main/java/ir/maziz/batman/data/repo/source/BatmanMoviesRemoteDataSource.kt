package ir.maziz.batman.data.repo.source

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.DetailBatmanResponse
import ir.maziz.batman.services.http.ApiService

class BatmanMoviesRemoteDataSource(val apiService: ApiService) : BatmanMoviesDataSource {
    override fun getMovies(apiKey: String, search: String): Single<BatmanResponse> =
        apiService.getMovies(apiKey, search)

    override fun getDetail(apiKey: String, imdbId: String): Single<DetailBatmanResponse> =
        apiService.getDetail(apiKey, imdbId)
}