package lk.kotlin.observable.property

class MutableAdditionObservableProperty<T>(
        val underlying: ObservableProperty<T>,
        val onWrite: (T)->Unit
) : MutableObservableProperty<T> {
    override var value: T
        get() = underlying.value
        set(value) {
            onWrite.invoke(value)
        }

    override fun add(element: (T) -> Unit): Boolean = underlying.add(element)
    override fun remove(element: (T) -> Unit): Boolean = underlying.remove(element)
}

fun <T> ObservableProperty<T>.withWrite(
        onWrite: (T) -> Unit
) = MutableAdditionObservableProperty(this, onWrite)