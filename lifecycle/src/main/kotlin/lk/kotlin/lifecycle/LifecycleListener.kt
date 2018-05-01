package lk.kotlin.lifecycle


/**
 * Listener for start and stop events on a lifecycle.
 *
 * Q: Why was this listener not separated into multiple lambdas?
 * A: Because you will always need to implement both sides or most likely experience an error, so to mitigate that, this design forces you to handle both.
 *  Created by jivie on 6/1/16.
 */
@Deprecated("Use a boolean observable property instead.")
interface LifecycleListener {
    /**
     * Called when the lifecycle starts.
     */
    fun onStart()

    /**
     * Called when the lifecycle stops.
     */
    fun onStop()
}