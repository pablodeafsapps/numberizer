package com.raywenderlich.numberizer.domainlayer.usecase

import arrow.core.Either
import arrow.core.left
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse

const val FETCH_NUMBER_FACT_UC_TAG = "fetchNumberFactUc"

class FetchNumberFactUc : DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse> {

    private lateinit var numberDataRepository: DomainlayerContract.Data.DataRepository<NumberFactResponse>

    override suspend fun run(params: NumberFactRequest?): Either<Failure, NumberFactResponse> =
        params?.let {
            numberDataRepository.fetchNumberFact(request = params)
        } ?: run {
            Failure.InputParamsError().left()
        }

}
