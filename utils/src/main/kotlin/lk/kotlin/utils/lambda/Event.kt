package lk.kotlin.utils.lambda

/**
 * An event that one can listen to is simply a mutable collection of lambda callbacks.
 * To fire an event, you simply need to
 */
typealias Event<T> = MutableCollection<(T)->Unit>