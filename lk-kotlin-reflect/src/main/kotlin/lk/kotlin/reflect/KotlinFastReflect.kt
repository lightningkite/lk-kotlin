package lk.kotlin.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses

private val KClassFastProperties = HashMap<KClass<*>, Map<String, KProperty1<*, *>>>()
/**
 * Retrieves the [memberProperties] in a faster manner than Kotlin's default by storing them in a map.
 */
@Suppress("UNCHECKED_CAST")
val <T : Any> KClass<T>.fastProperties
    get() = KClassFastProperties.getOrPut(this) {
        val list = this.memberProperties.toList()
        list.forEach { KPropertyOwner[it] = this }
        list.associateBy { it.name }
    } as Map<String, KProperty1<T, *>>


private val KClassFastMutableProperties = HashMap<KClass<*>, Map<String, KMutableProperty1<*, *>>>()
@Suppress("UNCHECKED_CAST")
        /**
         * Retrieves the mutable [memberProperties] in a faster manner than Kotlin's default by storing them in a map.
         */
val <T : Any> KClass<T>.fastMutableProperties
    get() = KClassFastMutableProperties.getOrPut(this) {
        val list = this.memberProperties.mapNotNull { it as? KMutableProperty1<T, *> }
        list.forEach { KPropertyOwner[it] = this }
        list.associateBy { it.name }
    } as Map<String, KMutableProperty1<T, *>>


private val KClassFastFunctions = HashMap<KClass<*>, List<KFunction<*>>>()
/**
 * Retrieves the [memberFunctions] in a faster manner than Kotlin's default by storing them in a map.
 */
val KClass<*>.fastFunctions
    get() = KClassFastFunctions.getOrPut(this) {
        val list = this.memberFunctions.toList()
        list.forEach { KFunctionOwner[it] = this }
        list
    }


private val KClassFastSuperclasses = HashMap<KClass<*>, List<KClass<*>>>()
/**
 * Retrieves the [superclasses] in a faster manner than Kotlin's default by storing them in a map.
 */
val KClass<*>.fastSuperclasses
    get() = KClassFastSuperclasses.getOrPut(this) { this.superclasses.toList() }


private val KClassFastIsEnum = HashMap<KClass<*>, Boolean>()
/**
 * Returns whether or not this class is an enum.
 */
val KClass<*>.isEnum
    get() = KClassFastIsEnum.getOrPut(this) { this.isSubclassOf(Enum::class) }


private val KClassEnumValues = HashMap<KClass<*>, Map<String, Enum<*>>>()
/**
 * Returns the enum's values.
 */
@Suppress("UsePropertyAccessSyntax")
val KClass<*>.enumValues
    get() = KClassEnumValues.getOrPut(this) { this.java.getEnumConstants().associate { (it as Enum<*>).let { it.name to it } } }


private val KProperty1FastType = HashMap<KProperty1<*, *>, TypeInformation>()
/**
 * Gets the [TypeInformation] of the return type.
 */
val KProperty1<*, *>.fastType get() = KProperty1FastType.getOrPut(this) { TypeInformation(this.returnType, this.annotations) }


val KPropertyOwner = HashMap<KProperty1<*, *>, KClass<*>>()
val KProperty1<*, *>.owner get() = KPropertyOwner[this]

val KFunctionOwner = HashMap<KFunction<*>, KClass<*>>()
val KFunction<*>.owner get() = KFunctionOwner[this]