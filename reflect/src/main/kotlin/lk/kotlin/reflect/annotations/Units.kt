package lk.kotlin.reflect.annotations

import lk.kotlin.reflect.classedName
import lk.kotlin.reflect.findType
import lk.kotlin.reflect.nameify
import java.util.*
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1


@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(AnnotationTarget.PROPERTY)
annotation class Units(val name: String)

private val kPropertyUnits = WeakHashMap<KAnnotatedElement, String?>()
val KAnnotatedElement.units: String?
    get() = kPropertyUnits.getOrPut(this) {
        annotations.findType<Units>()?.name
    }