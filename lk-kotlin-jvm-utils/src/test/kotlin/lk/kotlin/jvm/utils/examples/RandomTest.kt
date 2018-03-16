package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.random.random
import org.junit.Test
import java.util.*

class RandomTest {

    @Test
    fun testRandoms() {
        val random = Random()

        (0..10).random(random)
        (0.0..10.0).random(random)
        listOf('a', 'b', 'c').random(random)

        (0..10).random()
        (0.0..10.0).random()
        listOf('a', 'b', 'c').random()
    }
}