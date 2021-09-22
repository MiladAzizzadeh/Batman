package ir.maziz.batman.data.repo.source

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse

class BatmanMoviesLocalDataSource:BatmanMoviesDataSource {
    override fun getMovies(apiKey: String, search: String): Single<BatmanResponse> {
        TODO("Not yet implemented")
    }
}