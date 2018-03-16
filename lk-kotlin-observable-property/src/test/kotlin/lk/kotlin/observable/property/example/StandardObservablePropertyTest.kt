package lk.kotlin.observable.property.example

import lk.kotlin.observable.property.StandardObservableProperty
import lk.kotlin.observable.property.plusAssign
import org.junit.Test
import kotlin.test.assertEquals

class StandardObservablePropertyTest {

    @Test
    fun propertyExample() {

        val property = StandardObservableProperty(4)

        //Let's add a listener to the property
        var recentUpdate = 0
        property += {
            recentUpdate = it
        }

        property.value += 3
        //recentUpdate was notified!
        assertEquals(7, recentUpdate)
    }

    @Test
    fun propertyDelegateExample() {

        val property = StandardObservableProperty(4)
        //We can use property delegates if we want
        var x by property

        //Let's add a listener to the property
        var recentUpdate = 0
        property += {
            recentUpdate = it
        }

        //Don't try to shorten this, it won't compile when you use property delegates not inside a class
        @Suppress("ReplaceWithOperatorAssignment", "UNUSED_VALUE")
        x = x + 3

        //recentUpdate was notified!
        assertEquals(7, recentUpdate)
    }
}