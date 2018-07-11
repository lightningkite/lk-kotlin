package lk.kotlin.okhttp

@Deprecated("Just do this yourself.")
interface ErrorCaptureApi {

    val onError: ArrayList<(TypedResponse<*>) -> Unit>

    fun <T> (() -> TypedResponse<T>).captureError(): () -> TypedResponse<T> {
        return {
            val response = this.invoke()
            if (!response.isSuccessful()) {
                onError.forEach { it.invoke(response) }
            }
            response
        }
    }
}