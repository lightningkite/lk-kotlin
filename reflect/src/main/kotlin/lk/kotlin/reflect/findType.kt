package lk.kotlin.reflect


inline fun <reified T> Collection<*>.findType():T? = find { it is T } as? T