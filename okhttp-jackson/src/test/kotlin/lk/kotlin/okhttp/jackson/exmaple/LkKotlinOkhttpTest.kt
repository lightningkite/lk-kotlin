package lk.kotlin.okhttp.jackson.exmaple

import lk.kotlin.okhttp.OkHttpApi
import lk.kotlin.okhttp.TypedResponse
import lk.kotlin.okhttp.jackson.lambdaJackson
import org.junit.Test

class LkOkhttpTest {

    data class Post(
            var userId: Long = -1,
            var id: Long = -1,
            var title: String = "",
            var body: String = ""
    )

    /**An interface for the API, implemented by a real OkHttp API and a mock API**/
    interface APIInterface {
        fun getPosts(): () -> TypedResponse<List<Post>>
    }

    /**A real implementation of the API using OkHttp**/
    object ExampleAPI : OkHttpApi, APIInterface {
        //Specifies the base URL to use if using [OkHttpApi.requestBuilder].
        override val baseUrl: String = "https://jsonplaceholder.typicode.com"

        override fun getPosts() = requestBuilder("/posts")
                .get()
                .lambdaJackson<List<Post>>()
    }

    @Test
    fun testRealHttp() {
        val result = ExampleAPI.getPosts().invoke()
        if (result.isSuccessful()) {
            println("We got the data. Post titles: ${result.result!!.joinToString { it.title }}")
        } else {
            println("We failed to get the data - code was a ${result.code}.")
        }
    }


    /**A mock implementation of the API using OkHttp**/
    object ExampleMockAPI : APIInterface {
        override fun getPosts(): () -> TypedResponse<List<Post>> = {
            Thread.sleep(100)
            TypedResponse(200, listOf(Post(title = "Test Title")))
        }
    }

    @Test
    fun testMockAPI() {
        val result = ExampleMockAPI.getPosts().invoke()
        if (result.isSuccessful()) {
            println("We got the data. Post titles: ${result.result!!.joinToString { it.title }}")
        } else {
            println("We failed to get the data - code was a ${result.code}.")
        }
    }
}