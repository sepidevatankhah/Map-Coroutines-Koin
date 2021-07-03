package ir.nwise.app.domain.usecase.base

import ir.nwise.app.data.DefaultDispatcherProvider
import ir.nwise.app.data.DispatcherProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

typealias CompletionBlock<T> = UseCaseResult<T>.() -> Unit

abstract class UseCase<Param : Any?, Response>(
    private val coroutineScope: CoroutineScope = MainScope(),
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) {
    protected abstract suspend fun executeOnBackground(param: Param?): Response

    fun execute(param: Param? = null, block: CompletionBlock<Response>) {
        block(UseCaseResult.Loading)
        unsubscribe()
        coroutineScope.launch(dispatchers.job() + dispatchers.main()) {
            try {
                val result = withContext(dispatchers.io()) {
                    executeOnBackground(param)
                }
                block(UseCaseResult.Success(result))
            } catch (error: HttpException) {
                block(UseCaseResult.Error(error))
            } catch (cancellationException: CancellationException) {
                block(UseCaseResult.Error(cancellationException))
            } catch (e: Exception) {
                block(UseCaseResult.Error(e))
            }
        }
    }

    fun unsubscribe() {
        dispatchers.job().apply {
            cancelChildren()
            cancel()
        }
    }
}

sealed class UseCaseResult<out T> {
    object Loading : UseCaseResult<Nothing>()
    class Success<out T>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}

