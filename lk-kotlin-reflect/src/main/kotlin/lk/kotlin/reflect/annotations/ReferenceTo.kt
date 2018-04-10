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
annotation class ReferenceTo(val type:KClass<*>)

private val kPropertyReferenceTo = WeakHashMap<KAnnotatedElement, KClass<*>?>()
val KAnnotatedElement.referenceTo: KClass<*>?
    get() = kPropertyReferenceTo.getOrPut(this) {
        annotations.findType<ReferenceTo>()?.type
    }