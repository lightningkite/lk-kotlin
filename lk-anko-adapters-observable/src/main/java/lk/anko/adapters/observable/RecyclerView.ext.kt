@file:JvmName("LkAnkoAdaptersObservable")
@file:JvmMultifileClass

package lk.anko.adapters.observable



import android.support.v7.widget.RecyclerView
import lk.android.lifecycle.lifecycle
import lk.anko.adapters.SingleRecyclerViewAdapter
import lk.anko.adapters.TransitionRecyclerViewAdapter
import lk.anko.adapters.singleAdapter
import lk.anko.adapters.transitionAdapter
import lk.kotlin.observable.list.ObservableList
import lk.kotlin.observable.list.lifecycle.bind


/**
 * Makes a [RecyclerView.Adapter] displaying the given [list].
 * When the list is empty, the view generated by [makeEmptyView] will be shown.
 */
fun <T> RecyclerView.listAdapter(
        list: ObservableList<T>,
        makeEmptyView: SingleRecyclerViewAdapter.SRVAContext.() -> Unit,
        makeView: ListRecyclerViewAdapter.SRVAContext<T>.(ListRecyclerViewAdapter.ItemObservable<T>) -> Unit
): TransitionRecyclerViewAdapter {
    val adapter = transitionAdapter {
        val listAdapter = listAdapter(list, makeView)
        val emptyAdapter = singleAdapter(makeEmptyView)
        lifecycle.bind(list) { it: ObservableList<T> ->
            if (it.isEmpty()) {
                animate(emptyAdapter)
            } else {
                animate(listAdapter)
            }
        }
    }
    return adapter
}