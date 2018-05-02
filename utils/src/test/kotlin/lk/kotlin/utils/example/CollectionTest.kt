package lk.kotlin.utils.example

import lk.kotlin.utils.collection.Cache
import lk.kotlin.utils.collection.mapping
import lk.kotlin.utils.collection.mappingMutable
import lk.kotlin.utils.collection.mappingWriteOnly
import lk.kotlin.utils.lambda.invokeAll
import org.junit.Test
import kotlin.test.assertEquals

class CollectionTest {

    @Test
    fun testCache() {
        //Let's cache the value of every number multiplied by 5
        val fiveCache = Cache<Int, Int> { it * 5 }
        assertEquals(4 * 5, fiveCache[4])
        assertEquals(22 * 5, fiveCache[22])
        assertEquals(-18 * 5, fiveCache[-18])
    }

    @Test
    fun testCollectionWriteMappingOnly() {
        //Let's say we have an event where we are given integers, but we need strings.
        val exampleEvent = ArrayList<(Int) -> Unit>()

        var lastStringObtained = ""
        val listener = { it: String -> lastStringObtained = it }

        val wrappedEvent = exampleEvent.mappingWriteOnly<(Int) -> Unit, (String) -> Unit> { stringListener ->
            fun(input: Int) { stringListener.invoke(input.toString()) }
        }
        wrappedEvent += listener

        exampleEvent.invokeAll(4)
        assertEquals("4", lastStringObtained)

        //Let's stop listening.
        wrappedEvent -= listener

        exampleEvent.invokeAll(3)
        //Behold!  It has not changed
        assertEquals("4", lastStringObtained)
    }

    @Test
    fun eventsForCollectionWriteOnlyMappingExample() {
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

    @Test
    fun testIteratorMapping() {
        //Let's say I have an iterator from a list.
        val list = arrayListOf(1, 2, 3, 4, 5)
        val iterator = list.iterator() //note this is a mutable iterator

        val mappedIterator = iterator.mapping({ it.toString() })

        //Let's remove "3" from it!
        while (mappedIterator.hasNext()) {
            if (mappedIterator.next() == "3")
                mappedIterator.remove()
        }

        //It affects the original list!
        assert(!list.contains(3))
    }

    @Test
    fun testListMapping() {
        //Let's say I have a list.
        val list = arrayListOf(1, 2, 3)
        val listAsStrings = list.mappingMutable(
                read = { it.toString() },
                write = { it.toInt() }
        )

        //Equivalent to just using `map`, but it is mutable!
        assert(list.map { it.toString() }.toTypedArray() contentEquals listAsStrings.toTypedArray())


        //Let's append a 4 onto the list.
        list.add(4)

        //The as strings view still matches!
        assert(list.map { it.toString() }.toTypedArray() contentEquals listAsStrings.toTypedArray())


        //Let's remove all odd numbers.
        list.removeAll { it % 2 == 1 }

        //The as strings view still matches!
        assert(list.map { it.toString() }.toTypedArray() contentEquals listAsStrings.toTypedArray())


        //Let's add a "0" to the front of the view.
        listAsStrings.add(0, "0")

        //The update carries over to the original.
        assertEquals(0, list.first())
    }

    @Test
    fun testSetMapping() {
        //Let's say I have a set.
        val set = hashSetOf(1, 2, 3)
        val setAsStrings = set.mapping(
                read = { it.toString() },
                write = { it.toInt() }
        )

        //Equivalent to just using `map`, but it is mutable!
        assert(set.map { it.toString() }.toTypedArray() contentEquals setAsStrings.toTypedArray())


        //Let's append a 4 onto the set.
        set.add(4)

        //The as strings view still matches!
        assert(set.map { it.toString() }.toTypedArray() contentEquals setAsStrings.toTypedArray())


        //Let's remove all odd numbers.
        set.removeAll { it % 2 == 1 }

        //The as strings view still matches!
        assert(set.map { it.toString() }.toTypedArray() contentEquals setAsStrings.toTypedArray())


        //Let's add a "0" to the view
        setAsStrings.add("0")

        //The update carries over to the original.
        assert(set.contains(0))
    }
}