package lk.kotlin.observable.list

import lk.kotlin.observable.property.CombineObservableProperty2
import lk.kotlin.observable.property.MutableObservableProperty
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.withWrite

fun <T> ObservableList<T>.isInList(itemObs:ObservableProperty<T>):MutableObservableProperty<Boolean> = CombineObservableProperty2(this.onUpdate, itemObs){ items, item ->
    item in items
}.withWrite {
    val item = itemObs.value
    if(it){
        if(item !in this){
            this.add(item)
        }
    } else {
        if(item in this){
            this.remove(item)
        }
    }
}