package lk.kotlin.observable.list.lifecycle.example

import lk.kotlin.lifecycle.CloseableLifecycle
import lk.kotlin.observable.list.observableListOf
import lk.kotlin.observable.list.sorting
import org.junit.Test

class ExampleTest {

    @Test
    fun lifecycleDemo() {
        //Let's attach a view that needs cleanup to a lifecycle.
        val list = observableListOf('a', 'b', 'c')
        val lifecycle = CloseableLifecycle()
        val view = list.sorting { a, b -> a > b }

        //This will stop the view from listening.
        lifecycle.close()
    }
}