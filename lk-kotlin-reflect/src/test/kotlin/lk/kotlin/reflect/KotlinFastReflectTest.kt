package lk.kotlin.reflect

import org.junit.Test
import java.util.*
import kotlin.reflect.full.functions

class KotlinFastReflectTest() {

    class TestDataClass(
            var name: String = "",
            var timestamp: Date = Date(),
            var number: Int = 0
    ){
        fun test(value:Int){
            println("HI $value!")
        }
    }

    @Test
    fun testWrite() {
        val instance = TestDataClass()
        TestDataClass::class.fastMutableProperties.values.find { it.name == "number" }!!
                .reflectAsmSet(instance, 22)
    }

    @Test
    fun testInvoke() {
        val instance = TestDataClass()
        println(TestDataClass::class.functions.joinToString { it.name + it.parameters.size })
        println(TestDataClass::class.java.methods.joinToString { it.name + it.parameters.size })
        TestDataClass::class.fastFunctions.find { it.name == "test" }!!
                .reflectAsmInvoke(instance, 22)
        TestDataClass::class.fastFunctions.find { it.name == "equals" }!!
                .reflectAsmInvoke(instance, instance)
    }

    @Test
    fun testToken() {
        run{
            val info = typeInformation<List<TestDataClass>>()
            assert(info.kclass == List::class)
            assert(info.typeParameters.first().kclass == TestDataClass::class)
        }

        run{
            val info = typeInformation<Map<String, TestDataClass>>()
            assert(info.kclass == Map::class)
            assert(info.typeParameters[0].kclass == String::class)
            assert(info.typeParameters[1].kclass == TestDataClass::class)
        }
    }
}