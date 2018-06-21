package lk.kotlin.observable.property.lifecycle

import lk.kotlin.observable.property.ObservableProperty

/**
 * Runs [onOpen] when the observable becomes true, and [onClose] when it becomes false.
 * If the observable starts out as true, then [onOpen] is run.
 */
inline fun ObservableProperty<Boolean>.openCloseBinding(
        crossinline onOpen:()->Unit,
        crossinline onClose:()->Unit
): (Boolean) -> Unit {
    var state:Boolean = false
    val lambda = { newState:Boolean ->
        if(state != newState){
            if(newState){
                onOpen()
            } else {
                onClose()
            }
            state = newState
        }
    }
    add(lambda)
    return lambda
}