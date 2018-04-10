@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")

package lk.kotlin.reflect

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

inline fun KProperty1<*, *>.getUntyped(instance:Any) = (this as KProperty1<Any, Any?>).get(instance)
inline fun KMutableProperty1<*, *>.setUntyped(instance:Any, value:Any?) = (this as KMutableProperty1<Any, Any?>).set(instance, value)