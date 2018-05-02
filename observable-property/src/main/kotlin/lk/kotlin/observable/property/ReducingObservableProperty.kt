@file:JvmName("LkKotlinObservableProperty")
@file:JvmMultifileClass

package lk.kotlin.observable.property

/**
 * Creates an observable property from many, transforming the value via the given function, [reduce].
 */
fun <T, E> List<ObservableProperty<E>>.reducing(reduce: (List<E>) -> T): ObservableProperty<T>
        = CombineObservablePropertyBlind<T>(this){ reduce(map { it.value }) }