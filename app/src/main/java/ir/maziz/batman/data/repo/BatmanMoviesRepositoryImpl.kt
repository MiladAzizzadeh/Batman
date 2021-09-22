package ir.maziz.batman.data.repo

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.repo.source.BatmanMoviesRemoteDataSource

class BatmanMoviesRepositoryImpl(val batmanMoviesDataSource: BatmanMoviesRemoteDataSource) :
    BatmanMoviesRepository {
    override fun getMovies(apiKey: String, search: String): Single<BatmanResponse> =
        batmanMoviesDataSource.getMovies(apiKey, search)
}