package ir.maziz.batman.data.repo.source

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse

interface BatmanMoviesDataSource {
    fun getMovies(apiKey:String,search:String): Single<BatmanResponse>
}