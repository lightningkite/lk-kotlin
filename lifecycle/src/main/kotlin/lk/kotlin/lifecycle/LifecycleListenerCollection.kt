package lk.kotlin.lifecycle


/**
 * Listener for start and stop events on a lifecycle.
 * Created by jivie on 6/1/16.
 */
@Deprecated("Use a boolean observable property instead.")
class LifecycleListenerCollection(
        val collection: Collection<LifecycleListener>
) : LifecycleListener {
    override fun onStart() {
        collection.forEach { it.onStart() }
    }

    override fun onStop() {
        collection.forEach { it.onStop() }
    }
}