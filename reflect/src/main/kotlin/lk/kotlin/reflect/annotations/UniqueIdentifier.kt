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
annotation class UniqueIdentifier()

private val kPropertyUniqueIdentifier = WeakHashMap<KAnnotatedElement, Boolean>()
val KAnnotatedElement.uniqueIdentifier: Boolean
    get() = kPropertyUniqueIdentifier.getOrPut(this) {
        annotations.findType<UniqueIdentifier>() != null
    }