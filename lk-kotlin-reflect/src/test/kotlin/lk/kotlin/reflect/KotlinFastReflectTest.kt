package lk.kotlin.reflect

import org.junit.Test
import java.util.*

class KotlinFastReflectTest() {

    data class TestDataClass(
            var name: String = "",
            var timestamp: Date = Date(),
            var number: Int = 0
    )

    @Test
    fun testWrite() {
        val instance = TestDataClass()
        TestDataClass::class.fastMutableProperties.values.find { it.name == "number" }!!
                .reflectAsmSet(instance, 22)
    }
}