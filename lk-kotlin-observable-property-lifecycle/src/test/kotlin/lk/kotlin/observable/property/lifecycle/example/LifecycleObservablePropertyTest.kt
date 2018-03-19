package lk.kotlin.observable.property.lifecycle.example

import lk.kotlin.lifecycle.CloseableLifecycle
import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.lifecycle.bind
import org.junit.Test

class LifecycleObservablePropertyTest() {
    @Test
    fun example() {

        var initialValueReported = false
        var nextValueReported = false
        var afterUnbindNotReported = true

        val observable = StandardObservableProperty(0)

        //Let's say this represents the lifecycle of a view in the UI.
        val lifecycle = CloseableLifecycle()

        //This function should run immediately, as the lifecycle is started.
        lifecycle.bind(observable) {
            println("Value reported: $it")
            when (it) {
                0 -> initialValueReported = true
                1 -> nextValueReported = true
                2 -> afterUnbindNotReported = false
            }
        }

        //This will update the value of the observable and report it to our listeners.
        observable.value = 1

        //Lifecycle ends - listener unsubscribed
        lifecycle.close()

        //This will never make it to the listener.
        observable.value = 2

        assert(initialValueReported)
        assert(nextValueReported)
        assert(afterUnbindNotReported)
    }
}