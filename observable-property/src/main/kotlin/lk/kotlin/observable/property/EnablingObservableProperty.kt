package lk.kotlin.observable.property


/**
 * A collection that calls [enable] when the collection has an element in it, and [disable] when the collection is empty.
 * Created by joseph on 12/2/16.
 */
abstract class EnablingObservableProperty<E>() : ObservableProperty<E> {

    val list = ArrayList<(E) -> Unit>()

    abstract fun enable(): Unit
    abstract fun disable(): Unit

    var active = false
    fun checkUp() {
        if (!list.isEmpty() && !active) {
            active = true
            enable()
        }
    }

    fun checkDown() {
        if (list.isEmpty() && active) {
            active = false
            disable()
        }

    }

    override fun add(element: (E) -> Unit): Boolean {
        val result = list.add(element)
        checkUp()
        return result
    }

    override fun remove(element: (E) -> Unit): Boolean {
        val result = list.remove(element)
        checkDown()
        return result
    }

    open fun update(){
        val cached = value
        list.forEach { it.invoke(cached) }
    }
}