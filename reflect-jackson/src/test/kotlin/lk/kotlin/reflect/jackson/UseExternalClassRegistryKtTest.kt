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
            var enum: TestEnum = TestEnum.A,
            var list:List<Int> = listOf(),
            var mapList:Map<String, String> = mapOf()
    )

    @Test
    fun testRegister() {
        ExternalClassRegistry.registerWithSubtypes(HelloWorld::class)
        MyJackson.mapper.useExternalClassRegistry()

        println(ExternalClassRegistry.types)
    }

    @Test
    fun testSerialize() {
        ExternalClassRegistry.registerWithSubtypes(HelloWorld::class)
        MyJackson.mapper.useExternalClassRegistry()

        val subject = HelloWorld(z = HelloWorld(list = listOf(1,2,3)))
        println(subject.jacksonToString())
    }

    @Test
    fun testRoundTrip() {
        ExternalClassRegistry.registerWithSubtypes(HelloWorld::class)
        MyJackson.mapper.useExternalClassRegistry()

        val subject = HelloWorld(z = HelloWorld(list = listOf(1,2,3)))
        val json = subject.jacksonToString()
        println(json)
        val copy = json.jacksonFromString<HelloWorld>()
        println(copy)
        assert(copy == subject)
    }
}