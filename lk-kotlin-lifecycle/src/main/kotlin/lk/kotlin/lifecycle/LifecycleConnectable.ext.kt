@file:JvmName("LkKotlinLifecycle")
@file:JvmMultifileClass

package lk.kotlin.lifecycle


/**
 * Extensions to attach things to lifecycles.
 * Created by jivie on 6/1/16.
 */

/**
 * Adds the [item] to the [collection] when the lifecycle starts and removes it when the lifecycle stops.
 */
fun <T> LifecycleConnectable.listen(collection: MutableCollection<T>, item: T) {
    connect(object : LifecycleListener {
        override fun onStart() {
            collection.add(item)
        }

        override fun onStop() {
            collection.remove(item)
        }
    })
}

/**
 * Adds the [listener] to the events when the lifecycle starts and removes it when the lifecycle stops.
 */
fun <A, B> LifecycleConnectable.listen(eventA: MutableCollection<(A) -> Unit>, eventB: MutableCollection<(B) -> Unit>, listener: () -> Unit) {
    connect(object : LifecycleListener {
        val listenerA = { it: A -> listener() }
        val listenerB = { it: B -> listener() }

        override fun onStart() {
            eventA.add(listenerA)
            eventB.add(listenerB)
        }

        override fun onStop() {
            eventA.remove(listenerA)
            eventB.remove(listenerB)
        }
    })
}

/**
 * Adds the [listener] to the events when the lifecycle starts and removes it when the lifecycle stops.
 */
fun <A, B, C> LifecycleConnectable.listen(
        eventA: MutableCollection<(A) -> Unit>,
        eventB: MutableCollection<(B) -> Unit>,
        eventC: MutableCollection<(C) -> Unit>,
        listener: () -> Unit
) {
    connect(object : LifecycleListener {
        val listenerA = { it: A -> listener() }
        val listenerB = { it: B -> listener() }
        val listenerC = { it: C -> listener() }

        override fun onStart() {
            eventA.add(listenerA)
            eventB.add(listenerB)
            eventC.add(listenerC)
        }

        override fun onStop() {
            eventA.remove(listenerA)
            eventB.remove(listenerB)
            eventC.remove(listenerC)
        }
    })
}

/**
 * Adds the [listener] to the [event] when the lifecycle starts and removes it when the lifecycle stops.
 * Also runs the [listener] immediately with the [firstRunValue].
 */
fun <T> LifecycleConnectable.bind(event: MutableCollection<(T) -> Unit>, firstRunValue: T, listener: (T) -> Unit) {
    connect(object : LifecycleListener {
        override fun onStart() {
            event.add(listener)
            listener(firstRunValue)
        }

        override fun onStop() {
            event.remove(listener)
        }
    })
}

/**
 * Adds the [listener] to the [event] when the lifecycle starts and removes it when the lifecycle stops.
 * Also runs the [listener] immediately.
 */
fun LifecycleConnectable.bind(event: MutableCollection<() -> Unit>, listener: () -> Unit) {
    connect(object : LifecycleListener {
        override fun onStart() {
            event.add(listener)
            listener()
        }

        override fun onStop() {
            event.remove(listener)
        }
    })
}