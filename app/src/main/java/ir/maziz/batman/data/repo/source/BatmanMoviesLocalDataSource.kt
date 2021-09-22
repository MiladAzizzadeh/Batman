package ir.maziz.batman.data.repo.source

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.DetailBatmanResponse

class BatmanMoviesLocalDataSource:BatmanMoviesDataSource {
    override fun getMovies(apiKey: String, search: String): Single<BatmanResponse> {
        TODO("Not yet implemented")
    }

    override fun getDetail(apiKey: String, imdbId: String): Single<DetailBatmanResponse> {
        TODO("Not yet implemented")
    }
}