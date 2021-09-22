package ir.maziz.batman.data.repo

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse

interface BatmanMoviesRepository {
    fun getMovies(apiKey:String,search:String): Single<BatmanResponse>
}