package lk.kotlin.observable.property


class VirtualObservableProperty<T>(
        val getterFun: () -> T,
        val event: MutableCollection<(T) -> Unit>
) : EnablingObservableProperty<T>(), ObservableProperty<T> {

    override val value: T
        get() = getterFun()

    val listener = { t: T -> super.update() }

    override fun enable() {
        event.add(listener)
    }

    override fun disable() {
        event.remove(listener)
    }
}