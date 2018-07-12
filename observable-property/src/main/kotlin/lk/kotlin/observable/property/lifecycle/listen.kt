package lk.kotlin.observable.property.lifecycle

import lk.kotlin.observable.property.ObservableProperty

fun <T> Lifecycle.listen(collection: MutableCollection<T>, item: T) = openCloseBinding(
        onOpen = { collection.add(item) },
        onClose = { collection.remove(item) }
)

fun <T> Lifecycle.listen(
        property: ObservableProperty<T>,
        item: (T)->Unit
) = openCloseBinding(
        onOpen = { property.add(item) },
        onClose = { property.remove(item) }
)