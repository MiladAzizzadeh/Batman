package ir.maziz.batman.common

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BatmanSingleObserver<T>(val compositeDisposable: CompositeDisposable) :
    SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        print(e.toString())
    }
}