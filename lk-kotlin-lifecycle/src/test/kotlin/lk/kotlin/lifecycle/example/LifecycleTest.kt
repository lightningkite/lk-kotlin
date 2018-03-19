package lk.kotlin.lifecycle.examples

import lk.kotlin.lifecycle.CloseableLifecycle
import lk.kotlin.lifecycle.listen
import org.junit.Test

class LifecycleTest {

    @Test
    fun testLifecycle() {
        //Let's use a mutable list for testing purposes
        val mutableList = mutableListOf(1)

        //Let's start up a lifecycle!
        val lifecycle = CloseableLifecycle()

        //Using listen, we can attach something to a list while the lifecycle is on.
        lifecycle.listen(mutableList, 2)

        //Confirmed, 2 has been added to the list
        assert(mutableList.contains(2))

        //Let's close up the lifecycle.
        lifecycle.close()

        //The 2 has been removed from the list!
        assert(!mutableList.contains(2))
    }
}