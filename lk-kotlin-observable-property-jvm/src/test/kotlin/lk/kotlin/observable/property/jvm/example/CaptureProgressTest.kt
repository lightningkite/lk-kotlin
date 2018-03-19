package lk.kotlin.observable.property.jvm.example

import lk.kotlin.jvm.utils.async.Async
import lk.kotlin.jvm.utils.async.Blocking
import lk.kotlin.jvm.utils.async.invokeOn
import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.jvm.captureProgress
import lk.kotlin.observable.property.plusAssign
import org.junit.Test

class CaptureProgressTest {
    @Test
    fun example() {
        var didStart = false
        var didStop = false


        val longRunningOperation = {
            Thread.sleep(100)
        }
        val isRunning = StandardObservableProperty(false)

        println("Starting the operation...")

        isRunning += {
            if (it) {
                println("Operation started.")
                didStart = true
            } else {
                println("Operation complete.")
                didStop = true
            }
        }

        longRunningOperation
                .captureProgress(isRunning, Blocking)
                .invokeOn(Async)

        Thread.sleep(50)

        assert(didStart)
        assert(!didStop)

        Thread.sleep(100)

        assert(didStart)
        assert(didStop)
    }
}