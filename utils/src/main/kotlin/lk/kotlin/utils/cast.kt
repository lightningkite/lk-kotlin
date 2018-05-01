package lk.kotlin.utils


inline fun <reified T : Any> Any.cast() = this as T
inline fun <reified T : Any> Any.castOrNull() = this as? T