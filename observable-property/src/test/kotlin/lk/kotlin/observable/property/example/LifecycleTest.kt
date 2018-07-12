package lk.kotlin.observable.property.example

import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.lifecycle.Lifecycle
import lk.kotlin.observable.property.lifecycle.bind
import lk.kotlin.observable.property.lifecycle.listen
import org.junit.Test

class LifecycleTest() {
    @Test
    fun listenExample() {

        var initialValueNotReported = true
        var nextValueReported = false
        var afterUnbindNotReported = true

        val observable = StandardObservableProperty(0)

        //Let's say this represents the lifecycle of a view in the UI.
        val lifecycle = StandardObservableProperty(true)

        //The lambda below will now be run when the observable changes AND the lifecycle is on.
        lifecycle.listen(observable) {
            println("Value reported: $it")
            when (it) {
                0 -> initialValueNotReported = false
                1 -> nextValueReported = true
                2 -> afterUnbindNotReported = false
            }
        }

        //This will update the value of the observable and report it to our listeners.
        observable.value = 1

        //Lifecycle ends - listener unsubscribed
        lifecycle.value = false

        //This will never make it to the listener.
        observable.value = 2

        assert(initialValueNotReported)
        assert(nextValueReported)
        assert(afterUnbindNotReported)
    }

    @Test
    fun bindExample() {

        var initialValueReported = false
        var nextValueReported = false
        var afterUnbindNotReported = true

        val observable = StandardObservableProperty(0)

        //Let's say this represents the lifecycle of a view in the UI.
        val lifecycle = StandardObservableProperty(true)

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
        lifecycle.value = false

        //This will never make it to the listener.
        observable.value = 2

        assert(initialValueReported)
        assert(nextValueReported)
        assert(afterUnbindNotReported)
    }
}