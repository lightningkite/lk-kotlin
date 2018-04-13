package lk.kotlin.reflect.jackson

import lk.kotlin.jackson.MyJackson
import lk.kotlin.jackson.jacksonFromString
import lk.kotlin.jackson.jacksonToString
import lk.kotlin.reflect.ExternalClassRegistry
import lk.kotlin.reflect.annotations.ExternalName
import org.junit.Test
import java.util.*

class UseExternalClassRegistryKtTest {

    enum class TestEnum { A, B, C }

    @ExternalName("Hello")
    data class HelloWorld(
            var x: Int = 0,
            var y: String = "",
            var z: Any? = null,
            var d: Date = Date(),
            var sub: HelloWorld? = null,
            var enum: TestEnum = TestEnum.A
    )

    @Test
    fun testRegister() {
        ExternalClassRegistry.registerWithSubtypes(HelloWorld::class)
        println(ExternalClassRegistry.types)
    }

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