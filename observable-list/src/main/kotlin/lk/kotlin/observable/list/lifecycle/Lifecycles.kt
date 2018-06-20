
package lk.kotlin.observable.list.lifecycle

import lk.kotlin.observable.list.*
import lk.kotlin.observable.property.ObservableProperty
import lk.kotlin.observable.property.plusAssign

/**
 * Created by joseph on 1/31/18.
 */

/**
 * Creates a multi-grouped view of the list that lives during the lifecycle.
 */
fun <E, G, L> ObservableList<E>.multiGroupingBy(
        lifecycle: ObservableProperty<Boolean>,
        grouper: (E) -> Collection<G>,
        listWrapper: (ObservableList<E>) -> L
): ObservableListMultiGroupingBy<E, G, L> {
    val list = ObservableListMultiGroupingBy(this, grouper, listWrapper)
    lifecycle += {
        if(it) list.setup()
        else list.close()
    }
    return list
}

/**
 * Creates a multi-grouped view of the list that lives during the lifecycle.
 */
fun <E, G> ObservableList<E>.multiGroupingBy(lifecycle: ObservableProperty<Boolean>, grouper: (E) -> Collection<G>) = multiGroupingBy(lifecycle, grouper, { it })

/**
 * Creates a sorted view of the list that lives during the lifecycle.
 */
fun <E> ObservableList<E>.sorting(lifecycle: ObservableProperty<Boolean>, sorter: (E, E) -> Boolean): ObservableListSorted<E> {
    val list = ObservableListSorted(this, sorter)
    return list
}

/**
 * Creates a grouped view of the list that lives during the lifecycle.
 */
fun <E, G, L> ObservableList<E>.groupingBy(
        lifecycle: ObservableProperty<Boolean>,
        grouper: (E) -> G,
        listWrapper: (ObservableList<E>) -> L
): ObservableListGroupingBy<E, G, L> {
    val list = ObservableListGroupingBy(this, grouper, listWrapper)
    lifecycle += {
        if(it) list.setup()
        else list.close()
    }
    return list
}


/**
 * Creates a grouped view of the list that lives during the lifecycle.
 */
fun <E, G> ObservableList<E>.groupingBy(lifecycle: ObservableProperty<Boolean>, grouper: (E) -> G) = groupingBy(lifecycle, grouper, { it })


/**
 * Creates a flat-mapped view of the list that lives during the lifecycle.
 */
fun <S, E> ObservableList<S>.flatMapping(lifecycle: ObservableProperty<Boolean>, mapper: (S) -> ObservableList<E>): ObservableListFlatMapping<S, E> {
    val list = ObservableListFlatMapping(this, mapper)
    lifecycle += {
        if(it) list.setup()
        else list.close()
    }
    return list
}

/**
 * Creates a filtered view of the list that lives during the lifecycle.
 */
fun <E> ObservableList<E>.filtering(lifecycle: ObservableProperty<Boolean>): ObservableListFiltered<E> {
    val list = ObservableListFiltered(this)
    lifecycle += {
        if(it) list.setup()
        else list.close()
    }
    return list
}


/**
 * Creates a filtered view of the list that lives during the lifecycle.
 */
fun <E> ObservableList<E>.filtering(lifecycle: ObservableProperty<Boolean>, initFilter: (E) -> Boolean): ObservableListFiltered<E> {
    val list = ObservableListFiltered(this).apply {
        filter = initFilter
    }
    lifecycle += {
        if(it) list.setup()
        else list.close()
    }
    return list
}