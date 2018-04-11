package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.lambda.cooldown
import lk.kotlin.jvm.utils.lambda.parallel
import org.junit.Test
import kotlin.test.assertEquals

class LambdaTest {

    @Test
    fun testCooldown() {
        var timesRun = 0
        val lambda = { timesRun++; Unit }.cooldown(1000L)
        lambda.invoke()
        lambda.invoke()
        lambda.invoke()
        lambda.invoke()
        lambda.invoke()
        assertEquals(timesRun, 1)
    }

    @Test
    fun testParallel() {
        //Let's run a whole bunch of tasks in parallel.
        val results = (0..40)
                .map { it: Int -> { it * 2 } }
                .parallel()
                .invoke()

        val expected = (0..40).map { it * 2 }

        assert(results.toTypedArray() contentEquals expected.toTypedArray())
    }
}