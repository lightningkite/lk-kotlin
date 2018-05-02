package lk.kotlin.observable.property


/**
 * Combines several observable properties into one.
 * Created by joseph on 12/2/16.
 */
class CombineObservableProperty3<A, B, C, T>(
        val observableA: ObservableProperty<A>,
        val observableB: ObservableProperty<B>,
        val observableC: ObservableProperty<C>,
        val combine: (A, B, C) -> T
) : EnablingObservableProperty<T>(), ObservableProperty<T> {

    override var value = combine(observableA.value, observableB.value, observableC.value)

    override fun update() {
        value = combine(observableA.value, observableB.value, observableC.value)
        super.update()
    }

    val callbackA = { item: A ->
        update()
    }
    val callbackB = { item: B ->
        update()
    }
    val callbackC = { item: C ->
        update()
    }

    override fun enable() {
        value = combine(observableA.value, observableB.value, observableC.value)
        observableA.add(callbackA)
        observableB.add(callbackB)
        observableC.add(callbackC)
    }

    override fun disable() {
        observableA.remove(callbackA)
        observableB.remove(callbackB)
        observableC.remove(callbackC)
    }
}