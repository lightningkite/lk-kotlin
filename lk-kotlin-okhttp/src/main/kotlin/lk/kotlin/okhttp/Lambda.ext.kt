@file:JvmName("LkKotlinOkhttp")
@file:JvmMultifileClass

package lk.kotlin.okhttp


import java.util.concurrent.Executor

/**
 * Runs [onSuccess] on the [executor] with the successful result.
 */
inline fun <T> (() -> TypedResponse<T>).thenOnSuccess(executor: Executor, crossinline onSuccess: (T) -> Unit): () -> TypedResponse<T> {
    return {
        val response = this.invoke()
        if (response.isSuccessful()) {
            executor.execute { onSuccess(response.result!!) }
        }
        response
    }
}

/**
 * Runs [onFailure] on the [executor] with the successful result.
 */
inline fun <T> (() -> TypedResponse<T>).thenOnFailure(executor: Executor, crossinline onFailure: (TypedResponse<T>) -> Unit): () -> TypedResponse<T> {
    return {
        val response = this.invoke()
        if (!response.isSuccessful()) {
            executor.execute { onFailure(response) }
        }
        response
    }
}


/**
 * Allows you to chain successful calls from one to the next.
 */
inline fun <A, B> (() -> TypedResponse<A>).chain(crossinline otherLambdaGenerator: (A) -> () -> TypedResponse<B>): () -> TypedResponse<B> {
    return {
        val response = this.invoke()
        if (!response.isSuccessful()) {
            TypedResponse(response.code, null, response.headers, response.errorBytes, response.exception)
        } else {
            otherLambdaGenerator(response.result!!).invoke()
        }
    }
}

/**
 * Allows you to chain successful calls from one to the next.
 */
inline fun <A, B> (() -> TypedResponse<A>).chainTypeless(crossinline default: (TypedResponse<A>) -> B, crossinline otherLambdaGenerator: (A) -> () -> B): () -> B {
    return {
        val response = this.invoke()
        if (!response.isSuccessful()) {
            default(response)
        } else {
            otherLambdaGenerator(response.result!!).invoke()
        }
    }
}


/**
 * Transforms the result of successful calls.
 */
inline fun <A, B> (() -> TypedResponse<A>).transformResult(crossinline mapper: (A) -> B): () -> TypedResponse<B> {
    return {
        try {
            this.invoke().map(mapper)
        } catch (e: Exception) {
            TypedResponse(1, null, exception = e)
        }
    }
}