package lk.kotlin.reflect.jackson

import lk.kotlin.jackson.MyJackson
import lk.kotlin.jackson.jacksonFromString
import lk.kotlin.jackson.jacksonToString
import lk.kotlin.reflect.annotations.ExternalName
import org.junit.Test

class UseExternalClassRegistryKtTest {

    @ExternalName("Hello")
    data class HelloWorld(
            var x: Int = 0,
            var y: String = "",
            var z: Any? = null
    )

    @Test
    fun testSerialize() {
        MyJackson.mapper.useExternalClassRegistry()
        val subject = HelloWorld(z = HelloWorld())
        println(subject.jacksonToString())
    }

    @Test
    fun testRoundTrip() {
        MyJackson.mapper.useExternalClassRegistry()
        val subject = HelloWorld(z = HelloWorld())
        val json = subject.jacksonToString()
        val copy = json.jacksonFromString<HelloWorld>()
        println(copy)
        assert(copy == subject)
    }
}