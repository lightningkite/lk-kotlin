package lk.kotlin.observable.list.lifecycle

import lk.kotlin.observable.list.ObservableList
import lk.kotlin.observable.list.ObservableListListenerSet
import lk.kotlin.observable.list.addListenerSet
import lk.kotlin.observable.list.removeListenerSet
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.lifecycle.Lifecycle
import lk.kotlin.observable.property.lifecycle.bind
import lk.kotlin.observable.property.lifecycle.openCloseBinding


fun <T> Lifecycle.bind(observable: ObservableList<T>, listener: (ObservableList<T>) -> Unit) {
    bind(observable.onUpdate, listener)
}

fun <T> Lifecycle.bind(observable: ObservableList<T>, listenerSet: ObservableListListenerSet<T>) {
    this.openCloseBinding(
            onOpen = { observable.addListenerSet(listenerSet) },
            onClose = { observable.removeListenerSet(listenerSet) }
    )
}