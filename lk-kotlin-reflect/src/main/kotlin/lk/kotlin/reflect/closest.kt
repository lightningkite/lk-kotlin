package lk.kotlin.reflect

import kotlin.reflect.KClass


fun Collection<KClass<*>>.closest(type: KClass<*>): KClass<*> {
    if (contains(type)) return type
    val queue = arrayListOf(type)
    while (queue.isNotEmpty()) {
        val next = queue.removeAt(0)
        if (next == Any::class) continue
        if (contains(next)) return next
        //add all supertypes
        queue.addAll(next.fastSuperclasses)
    }
    return Any::class
}