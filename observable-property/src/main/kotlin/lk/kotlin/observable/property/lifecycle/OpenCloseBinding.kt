package lk.kotlin.observable.property.lifecycle

/**
 * Runs [onOpen] when the observable becomes true, and [onClose] when it becomes false.
 * If the observable starts out as true, then [onOpen] is run.
 */
inline fun Lifecycle.openCloseBinding(
        crossinline onOpen:()->Unit,
        crossinline onClose:()->Unit
): (Boolean) -> Unit {
    var state = false
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
    lambda.invoke(this.value)
    return lambda
}