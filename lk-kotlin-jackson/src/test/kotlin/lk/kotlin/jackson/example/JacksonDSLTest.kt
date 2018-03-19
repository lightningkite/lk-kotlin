package lk.kotlin.jackson.examples

import lk.kotlin.jackson.jacksonArray
import lk.kotlin.jackson.jacksonObject
import org.junit.Test
import kotlin.test.assertEquals

class JacksonDSLTest {

    data class ExampleDataClass(val x: Int = 1)


    @Test
    fun testJacksonObjectAny() {

        val actual = jacksonObject(
                "key" to "value",
                "anotherKey" to 3.14,
                "complex" to jacksonObject(
                        "nested" to true
                ),
                "native" to ExampleDataClass(x = 2)
        )

        val expected = """{
            |   "key":"value",
            |   "anotherKey":3.14,
            |   "complex":{
            |       "nested":true
            |   },
            |   "native":{
            |       "x":2
            |   }
            |}""".trimMargin().filter { !it.isWhitespace() }

        assertEquals(expected = expected, actual = actual.toString())
    }


    @Test
    fun testJacksonArrayAny() {

        val actual = jacksonArray(
                "value",
                3.14,
                jacksonArray(
                        true
                ),
                ExampleDataClass(x = 2)
        )

        val expected = """[
            |   "value",
            |   3.14,
            |   [
            |       true
            |   ],
            |   {
            |       "x":2
            |   }
            |]""".trimMargin().filter { !it.isWhitespace() }

        assertEquals(expected = expected, actual = actual.toString())
    }
}