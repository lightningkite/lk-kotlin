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
annotation class Password()

private val kPropertyPassword = WeakHashMap<KAnnotatedElement, Boolean>()
val KAnnotatedElement.password: Boolean
    get() = kPropertyPassword.getOrPut(this) {
        annotations.findType<Password>() != null
    }