package lk.kotlin.reflect

import org.junit.Test
import java.lang.reflect.Method
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.javaGetter

class KotlinFastReflectTest() {

    class TestDataClass(
            var name: String = "",
            var timestamp: Date = Date(),
            var number: Int = 0
    ) {
        fun test(value: Int) {
            println("HI $value!")
        }
    }
    var unoptimize = 10
    val classReference by lazy{ if(unoptimize > 3) TestDataClass::class else TestDataClass::class }

    inline fun performance(iterations: Int, warmUp: Int = iterations, code: () -> Unit): Double {
        repeat(warmUp) {
            code()
        }
        val startTime = System.nanoTime()
        repeat(iterations) {
            code()
        }
        return (System.nanoTime() - startTime) / iterations.toDouble()
    }

    @Test
    fun testPropertyOrder() {
        println(TestDataClass::class.fastMutableProperties.values.joinToString { it.name })
    }

    @Test
    fun fetchClass() {
        val handle = TestDataClass::class
        val ns = performance(10000000) {
            TestDataClass::class.simpleName
        } - performance(10000000) {
            handle.simpleName
        }
        println("Direct cost: $ns")
    }

    @Test
    fun useJavaFetch() {
        val map = HashMap<KProperty1<*, *>, Method?>()
        map[TestDataClass::name] = TestDataClass::name.javaGetter
        val nsJava = performance(10000000) {
            TestDataClass::name.javaGetter
        }
        val nsCache = performance(10000000) {
            map[TestDataClass::name]
        }
        println("Get javaGetter nsJava $nsJava nsCache $nsCache")
    }

    @Test
    fun compareCreatePerformance() {
        val cache = HashMap<KClass<*>, KFunction<*>>()
        cache[classReference] = classReference.constructors.singleOrNull { it.parameters.all(KParameter::isOptional) }
                ?: throw IllegalArgumentException("Class should have a single no-arg constructor: $this")
        val nsCached =  performance(1000000) {
            cache[classReference]!!.callBy(emptyMap())
        }
        val nsKotlin = performance(1000000) {
            classReference.java.newInstance()
        }
        val nsJava = performance(1000000) {
            classReference.createInstance()
        }
        println("Kotlin: $nsKotlin Java: $nsJava Cached: $nsCached")
    }

    @Test
    fun compareGetPerformance() {
        val instance = TestDataClass()
        val field = TestDataClass::class.fastProperties.values.find { it.name == "number" }!!
        val nsJava = performance(100000000) {
            field.getJava(instance)
        }
        val nsKotlin = performance(100000000) {
            field.getUntyped(instance)
        }
        println("Kotlin: $nsKotlin Java: $nsJava")
    }

    @Test
    fun comparePropertyPerformance(){
        val type = TestDataClass::class
        val nsCached = performance(1000000) {
            type.fastMutableProperties
        }
        val nsKotlin = performance(1000000) {
            type.memberProperties
        }
        println("Kotlin: $nsKotlin Cached: $nsCached")
    }

    @Test
    fun compareSuperclassesPerformance(){
        val type = TestDataClass::class
        val nsCached = performance(1000000) {
            type.fastSuperclasses
        }
        val nsKotlin = performance(1000000) {
            type.superclasses
        }
        println("Kotlin: $nsKotlin Cached: $nsCached")
    }

    @Test
    fun compareFunctionsPerformance(){
        val type = TestDataClass::class
        val nsCached = performance(1000000) {
            type.fastFunctions
        }
        val nsKotlin = performance(1000000) {
            type.functions
        }
        println("Kotlin: $nsKotlin Cached: $nsCached")
    }

    @Test
    fun testToken() {
        run {
            val info = typeInformation<List<TestDataClass>>()
            assert(info.kclass == List::class)
            assert(info.typeParameters.first().kclass == TestDataClass::class)
        }

        run {
            val info = typeInformation<Map<String, TestDataClass>>()
            assert(info.kclass == Map::class)
            assert(info.typeParameters[0].kclass == String::class)
            assert(info.typeParameters[1].kclass == TestDataClass::class)
        }
    }
}