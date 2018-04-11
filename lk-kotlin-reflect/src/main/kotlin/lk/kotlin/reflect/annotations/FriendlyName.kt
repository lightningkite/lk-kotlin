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
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class FriendlyName(val name: String)

private val kPropertyFriendlyName = WeakHashMap<KAnnotatedElement, String>()
val KAnnotatedElement.friendlyName: String
    get() = kPropertyFriendlyName.getOrPut(this) {
        annotations.findType<FriendlyName>()?.name ?: run{
            when(this){
                is KClass<*> -> classedName?.nameify() ?: toString().nameify()
                is KProperty1<*, *> -> name.nameify()
                else -> toString().nameify()
            }
        }
    }