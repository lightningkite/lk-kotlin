package lk.kotlin.utils.examples

import lk.kotlin.utils.lambda.invokeAll
import lk.kotlin.utils.lambda.then
import org.junit.Test
import kotlin.test.assertEquals

class LambdaTest {

    @Test
    fun testThen() {
        //Weird calculation, here we go!
        val calculation = { 3 } then { it + 3 } then { it * 4 } then { it.toString() }
        val actual = calculation.invoke()

        //This is the equivalent, but not using the `then`.
        val expected = ((3 + 3) * 4).toString()

        assertEquals(expected, actual)
    }

    @Test
    fun eventsExample() {
        //Here's an event!
        val onIntFound = ArrayList<(Int) -> Unit>()

        //Add a listener:
        onIntFound += { it: Int -> println("A: The integer $it was found.") }

        //Add a listener that sets `latestInteger` to whatever was last reported
        var latestInteger = 0
        val updateLatestIntegerListener = { it: Int -> latestInteger = it }
        onIntFound += updateLatestIntegerListener

        //Let's notify all of the listeners that we found a 1.
        onIntFound.invokeAll(1)
        //Look!  `latestInteger` has indeed been updated!
        assert(latestInteger == 1)

        onIntFound.invokeAll(2)
        assert(latestInteger == 2)

        onIntFound.invokeAll(3)
        assert(latestInteger == 3)

        //What if I don't want `latestInteger` to update from it anymore?
        //Let's stop listening to the event.
        onIntFound -= updateLatestIntegerListener

        //The other listener is still attached, so the println listener above still occur, but latestInteger will stay the same.
        onIntFound.invokeAll(4)
        assert(latestInteger == 3) //still hasn't changed
    }
}