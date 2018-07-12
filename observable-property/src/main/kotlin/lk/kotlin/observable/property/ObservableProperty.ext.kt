@file:JvmName("LkKotlinObservableProperty")
@file:JvmMultifileClass

package lk.kotlin.observable.property

import lk.kotlin.jvm.utils.async.Async
import lk.kotlin.jvm.utils.async.invokeOn
import lk.kotlin.jvm.utils.async.thenOn
import lk.kotlin.utils.lambda.then
import java.util.concurrent.Executor


operator fun <T> ObservableProperty<T>.plusAssign(lambda: (T) -> Unit): Unit {
    add(lambda)
}

operator fun <T> ObservableProperty<T>.minusAssign(lambda: (T) -> Unit): Unit {
    remove(lambda)
}

/**
 * Both adds a listener and invokes the lambda immediately with the current value of the observable.
 */
fun <A> ObservableProperty<A>.addAndInvoke(lambda: (A) -> Unit) {
    add(lambda)
    lambda.invoke(value)
}

/**
 * Invokes the lambda on the [runOn] thread (background by default) and the result is placed into
 * the lambda that is returned on the [updateOn] thread.  In most cases, you will want [updateOn]
 * to be the UI thread.
 */
fun <T> (()->T).invokeObservable(updateOn: Executor, runOn: Executor = Async):ObservableProperty<T?>{
    val obs = StandardObservableProperty<T?>(null)
    this.thenOn(updateOn) {
        obs.value = it
    }.invokeOn(runOn)
    return obs
}