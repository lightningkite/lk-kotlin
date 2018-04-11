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
annotation class TypeDetail(vararg val details:String)

private val kPropertyTypeDetail = WeakHashMap<KAnnotatedElement, Array<String>>()
@Suppress("UNCHECKED_CAST")
val KAnnotatedElement.typeDetail: Array<String>
    get() = kPropertyTypeDetail.getOrPut(this) {
        annotations.findType<TypeDetail>()?.details?.let{ it as Array<String> } ?: arrayOf()
    }