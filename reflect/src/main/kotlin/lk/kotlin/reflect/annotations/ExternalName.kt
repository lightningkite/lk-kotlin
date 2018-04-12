package lk.kotlin.reflect.annotations

import lk.kotlin.reflect.classedName
import lk.kotlin.reflect.findType
import java.util.*
import kotlin.reflect.KAnnotatedElement


@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class ExternalName(val name: String)

private val kPropertyExternalName = WeakHashMap<KAnnotatedElement, String>()
val KAnnotatedElement.externalName: String
    get() = kPropertyExternalName.getOrPut(this) {
        annotations.findType<ExternalName>()?.name ?: run{
            when(this){
                is kotlin.reflect.KClass<*> -> classedName ?: toString()
                is kotlin.reflect.KProperty1<*, *> -> name
                else -> toString()
            }
        }
    }
