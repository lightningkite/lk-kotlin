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
annotation class ExternalName(val name: String)

private val kPropertyExternalName = WeakHashMap<KAnnotatedElement, String>()
val KAnnotatedElement.externalName: String
    get() = kPropertyExternalName.getOrPut(this) {
        annotations.findType<ExternalName>()?.name ?: run{
            when(this){
                is kotlin.reflect.KClass<*> -> simpleName ?: toString()
                is kotlin.reflect.KProperty1<*, *> -> name
                else -> toString()
            }
        }
    }
