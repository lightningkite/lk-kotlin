package list.example

import lk.kotlin.observable.list.*
import org.junit.Test

class ExampleTest {

    @Test
    fun exampleObservableList() {
        //The real tests are elsewhere, this is primarily an example.
        //These have been tested quite heavily.  No risks are to be taken with this.

        val list = observableListOf('a', 'b', 'c')

        //Add listeners
        list.onAdd += { item, position -> println("onAddListener: $item at $position") }
        list.onRemove += { item, position -> println("onRemoveListener: $item at $position") }
        list.onChange += { old, item, position -> println("onChangeListener: $old to $item at $position") }
        list.onMove += { item, oldPosition, position -> println("onMoveListener: $item at $oldPosition to $position") }
        list.onReplace += { theList -> println("onReplaceListener: $theList") }

        //Let's modify the list.

        list.add('d') //onAddListener: d at 3
        list.add('e') //onAddListener: e at 4

        list.removeAll { it.toInt() % 2 == 0 }
        //onRemoveListener: b at 1
        //onRemoveListener: d at 2 //NOTE that the index is modified as it goes!

        //You can tell the list to patch an update through
        //in the event that the items are mutable and you have changed one.
        list.updateAt(0)
        list.update('c')
    }

    @Test
    fun mapView() {
        //You can have an alternate view on an observable list.  This one filters out elements.
        val list = observableListOf('a', 'b', 'c')
        val view = list.mapping(
                read = { it.toInt() }
        )
        assert(list.map { it.toInt() }.toTypedArray() contentEquals view.toTypedArray())

        //This view does not need to be closed.
    }

    @Test
    fun filterView() {
        //You can have an alternate view on an observable list.  This one filters out elements.
        val list = observableListOf('a', 'b', 'c')
        val view = list.filtering { it != 'a' }
        assert(arrayOf('b', 'c') contentEquals view.toTypedArray())

        //Make sure you close your view so that it no longer listens to the original list.
        view.close()
    }

    @Test
    fun sortView() {
        //You can have an alternate view on an observable list.  This one sorts elements.
        val list = observableListOf('a', 'b', 'c')
        val view = list.sorting { a, b -> a > b }
        assert(arrayOf('c', 'b', 'a') contentEquals view.toTypedArray())

        //Make sure you close your view so that it no longer listens to the original list.
        view.close()
    }

    @Test
    fun groupView() {
        //You can have an alternate view on an observable list.  This one groups elements.
        val list = observableListOf('a', 'b', 'c', 'd')
        val view = list.groupingBy { it >= 'c' }
        println(view.joinToString("\n") { it.second.joinToString() })
        assert(arrayOf(arrayOf('a', 'b'), arrayOf('c', 'd')) contentDeepEquals view.map { it.second.toTypedArray() }.toTypedArray())

        //Make sure you close your view so that it no longer listens to the original list.
        view.close()
    }
}