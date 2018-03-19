package lk.kotlin.lifecycle


/**
 * Used to interface with different components that have different lifecycles.
 * Represents an object that has a lifecycle, or in other words, starts and stops activity.
 * Created by jivie on 6/1/16.
 */
interface LifecycleConnectable {
    fun connect(listener: LifecycleListener)
}