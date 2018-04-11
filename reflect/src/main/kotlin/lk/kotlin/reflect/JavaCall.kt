package lk.kotlin.reflect

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaSetter

@Suppress("NOTHING_TO_INLINE")
inline fun <T, V> KProperty1<T, V>.getJava(receiver:T):V = javaGetter!!.invoke(receiver) as V

@Suppress("NOTHING_TO_INLINE")
inline fun <T, V> KMutableProperty1<T, V>.setJava(receiver:T, value:V) = javaSetter!!.invoke(receiver, value)