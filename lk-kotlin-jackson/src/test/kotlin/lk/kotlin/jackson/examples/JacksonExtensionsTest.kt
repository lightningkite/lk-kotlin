package lk.kotlin.jackson.examples

import lk.kotlin.jackson.*
import org.junit.Test
import kotlin.test.assertEquals

class JacksonExtensionsTest {

    data class ExampleDataClass(val x: Int = 1)


    @Test
    fun testJacksonToString() {

        val actual = ExampleDataClass(x = 2).jacksonToString()

        val expected = """{"x":2}"""

        assertEquals(expected = expected, actual = actual)
    }


    @Test
    fun testJacksonToNode() {

        val actual = ExampleDataClass(x = 2).jacksonToNode()

        val expected = jacksonObject("x" to 2)

        assertEquals(expected = expected, actual = actual)
    }


    @Test
    fun testJacksonFromString() {

        val actual = """{"x":2}""".jacksonFromString<ExampleDataClass>()

        val expected = ExampleDataClass(x = 2)

        assertEquals(expected = expected, actual = actual)
    }


    @Test
    fun testToValue() {

        val actual = jacksonObject("x" to 2).toValue<ExampleDataClass>()

        val expected = ExampleDataClass(x = 2)

        assertEquals(expected = expected, actual = actual)
    }


}