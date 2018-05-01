package lk.kotlin.observable.property.lifecycle

import lk.kotlin.observable.property.ObservableProperty

abstract class OpenCloseBinding(startState:Boolean): (Boolean)->Unit{
    var state:Boolean = false
    init{
        invoke(startState)
    }

    abstract fun onOpen()
    abstract fun onClose()

    final override fun invoke(newState: Boolean) {
        if(state != newState){
            if(newState){
                onOpen()
            } else {
                onClose()
            }
            state = newState
        }
    }
}
inline fun ObservableProperty<Boolean>.openCloseBinding(
        crossinline onOpen:()->Unit,
        crossinline onClose:()->Unit
)
    = object : OpenCloseBinding(this.value){
    override fun onOpen() = onOpen.invoke()
    override fun onClose() = onClose.invoke()
}