package com.raywenderlich.numberizer.domainlayer

import arrow.core.Either
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface DomainlayerContract {

    interface Presentation {

        interface UseCase<in T, out S> {

            fun invoke(scope: CoroutineScope, params: T?, onResult: (Either<Failure, S>) -> Unit) {
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }

            suspend fun run(params: T? = null): Either<Failure, S>
        }

    }

    interface Data {

        companion object {
            const val DATA_REPOSITORY_TAG = "dataRepository"
        }

        interface DataRepository<out S> {

            suspend fun fetchNumberFact(request: NumberFactRequest): Either<Failure, S>

        }

    }

}