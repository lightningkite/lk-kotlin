package lk.kotlin.okhttp.example

import lk.kotlin.okhttp.OkHttpApi
import lk.kotlin.okhttp.TypedResponse
import lk.kotlin.okhttp.lambdaString
import org.junit.Test

class LkOkhttpTest {

    /**An interface for the API, implemented by a real OkHttp API and a mock API**/
    interface APIInterface {
        fun getPosts(): () -> TypedResponse<String>
    }

    /**A real implementation of the API using OkHttp**/
    object ExampleAPI : OkHttpApi, APIInterface {
        //Specifies the base URL to use if using [OkHttpApi.requestBuilder].
        override val baseUrl: String = "https://jsonplaceholder.typicode.com"

        override fun getPosts() = requestBuilder("/posts")
                .get()
                .lambdaString()
    }

    @Test
    fun testRealHttp() {
        val result = ExampleAPI.getPosts().invoke()
        if (result.isSuccessful()) {
            println("We got the data: ${result.result}")
        } else {
            println("We failed to get the data - code was a ${result.code}.")
        }
    }


    /**A mock implementation of the API using OkHttp**/
    object ExampleMockAPI : APIInterface {
        override fun getPosts(): () -> TypedResponse<String> = {
            Thread.sleep(100)
            TypedResponse(200, """[{
    "userId": 10,
    "id": 98,
    "title": "laboriosam dolor voluptates",
    "body": "doloremque ex facilis sit sint culpa\nsoluta assumenda eligendi non ut eius\nsequi ducimus vel quasi\nveritatis est dolores"
  }]""")
        }
    }

    @Test
    fun testMockAPI() {
        val result = ExampleMockAPI.getPosts().invoke()
        if (result.isSuccessful()) {
            println("We got the data: ${result.result}")
        } else {
            println("We failed to get the data - code was a ${result.code}.")
        }
    }
}