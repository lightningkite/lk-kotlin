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
annotation class EstimatedLength(val length:Int)

private val kPropertyEstimatedLength = WeakHashMap<KAnnotatedElement, Int?>()
val KAnnotatedElement.estimatedLength: Int?
    get() = kPropertyEstimatedLength.getOrPut(this) {
        annotations.findType<EstimatedLength>()?.length
    }