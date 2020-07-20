package com.raywenderlich.numberizer.datalayer.repository

import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.raywenderlich.numberizer.datalayer.datasource.NumberFactDataSource
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

private const val DEFAULT_INTEGER_VALUE = 0
private const val SERVER_ERROR_CODE = 400
private const val DEFAULT_STRING_VALUE = "none"

@ExperimentalCoroutinesApi
class NumberDataRepositoryTest {

    @Test
    fun `When 'fetchNumberFact' is invoked and the data-source returns data -- A 'NumberFactResponse' instance is returned`() =
        runBlockingTest {
            // given
            val mockNumberFactDataSource: NumberFactDataSource = mock {
                onBlocking { fetchNumberFact(request = any()) }.doReturn(getDummyNumberFactResponse())
            }
            val repository: DomainlayerContract.Data.DataRepository<NumberFactResponse> =
                NumberDataRepository.apply { numberFactDataSource = mockNumberFactDataSource }
            // when
            val response = repository.fetchNumberFact(request = getDummyNumberFactRequest())
            // then
            Assert.assertTrue(response.isRight() && (response as? Either.Right<NumberFactResponse>) != null)
        }

    @Test
    fun `When 'fetchNumberFact' is invoked and the data-source returns an error -- A 'NoData' error is returned`() =
        runBlockingTest {
            // given
            val mockNumberFactDataSource: NumberFactDataSource = mock {
                onBlocking { fetchNumberFact(request = any()) }.doReturn(
                    getDummyNumberFactResponseError()
                )
            }
            val repository: DomainlayerContract.Data.DataRepository<NumberFactResponse> =
                NumberDataRepository.apply { numberFactDataSource = mockNumberFactDataSource }
            // when
            val response = repository.fetchNumberFact(request = getDummyNumberFactRequest())
            // then
            Assert.assertTrue(response.isLeft() && (response as? Either.Left<Failure>)?.a is Failure.NoData)
        }

    private fun getDummyNumberFactRequest() = NumberFactRequest(
        number = DEFAULT_INTEGER_VALUE
    )

    private fun getDummyNumberFactResponse() = Response.success(DEFAULT_STRING_VALUE)

    private fun getDummyNumberFactResponseError() = getDummyRetrofitError()

    private fun getDummyRetrofitError(): Response<String> = Response.error(
        SERVER_ERROR_CODE,
        "{\"dummyKey\":[\"dummyValue\"]}".toResponseBody("application/json".toMediaTypeOrNull())
    )

}