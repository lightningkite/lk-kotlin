package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.async.Async
import lk.kotlin.jvm.utils.async.invokeFuture
import lk.kotlin.jvm.utils.async.invokeOn
import lk.kotlin.jvm.utils.async.thenOn
import org.junit.Test

class AsyncLambdaTest {

    @Test
    fun testInvokeOnAsync() {
        var fastHappened = false
        var slowHappened = false

        //slow
        {
            Thread.sleep(100)
            assert(fastHappened)
            slowHappened = true
            println("I'm second!")
        }.invokeOn(Async)

        //fast
        fastHappened = true
        println("I'm first!")

        //wait for slow to catch up
        Thread.sleep(150)

        //should print
        //I'm first!
        //I'm second!
    }

    @Test
    fun testInvokeFutureAsync() {
        val future = {
            (0..10000).sum()
        }.invokeFuture(Async)

        val result = future.get()
        println(result)
    }

    @Test
    fun testThenAsync() {
        {
            println("Main Thread")
        }.thenOn(Async) {
            println("Async Thread")
        }.thenOn(Async) {
            println("Another Async Thread")
        }.invoke()

        //Wait for tasks to finish
        Thread.sleep(100)
    }
}