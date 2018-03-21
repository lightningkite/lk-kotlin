package lk.kotlin.reflect

import com.esotericsoftware.reflectasm.ConstructorAccess
import com.esotericsoftware.reflectasm.FieldAccess
import com.esotericsoftware.reflectasm.MethodAccess
import lk.kotlin.utils.collection.Cache
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
        list.forEach {
            it.fastSetup(this)
        }
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
        list.forEach {
            it.fastSetup(this)
        }
        list.associateBy { it.name }
    } as Map<String, KMutableProperty1<T, *>>

private val KClassFastFunctions = HashMap<KClass<*>, List<KFunction<*>>>()
/**
 * Retrieves the [memberFunctions] in a faster manner than Kotlin's default by storing them in a map.
 */
val KClass<*>.fastFunctions
    get() = KClassFastFunctions.getOrPut(this) {
        val list = this.memberFunctions.toList()
        list.forEach {
            it.fastSetup(this)
        }
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

private val MethodAccessCache = Cache<KClass<*>, MethodAccess> { MethodAccess.get(it.java) }
/**
 * Returns a cached [MethodAccess] from ReflectASM.
 */
val KClass<*>.reflectAsmMethodAccess get() = MethodAccessCache.get(this)
private val FieldAccessCache = Cache<KClass<*>, FieldAccess> { FieldAccess.get(it.java) }
/**
 * Returns a cached [FieldAccess] from ReflectASM.
 */
val KClass<*>.reflectAsmFieldAccess get() = FieldAccessCache.get(this)
private val ConstructorAccessCache = Cache<KClass<*>, ConstructorAccess<*>> { ConstructorAccess.get(it.java) }
@Suppress("UNCHECKED_CAST")
        /**
         * Returns a cached [ConstructorAccess] from ReflectASM.
         */
val <T : Any> KClass<T>.reflectAsmConstructorAccess
    get() = ConstructorAccessCache.get(this) as ConstructorAccess<T>

private val KProperty1ReflectAsmGet = HashMap<KProperty1<*, *>, (Any) -> Any?>()
/**
 * Gets the value of the property using ReflectASM.
 */
fun KProperty1<*, *>.reflectAsmGet(instance: Any) = KProperty1ReflectAsmGet[this]!!.invoke(instance)

private val KProperty1ReflectAsmSet = HashMap<KProperty1<*, *>, (Any, Any?) -> Unit>()
/**
 * Sets the value of the property using ReflectASM.
 */
fun KMutableProperty1<*, *>.reflectAsmSet(instance: Any, value: Any?) = KProperty1ReflectAsmSet[this]!!.invoke(instance, value)

/**
 * Sets up the property to use ReflectASM.
 */
private fun KProperty1<*, *>.fastSetup(kClass: KClass<*>) {
    val methodAccess = kClass.reflectAsmMethodAccess
    val getMethodName = ("get" + this.name.capitalize()).takeIf { it in methodAccess.methodNames }
            ?: ("is" + this.name.capitalize()).takeIf { it in methodAccess.methodNames }
    val indexGet = methodAccess.getIndex(getMethodName)
    KProperty1ReflectAsmGet.putIfAbsent(this) { instance: Any -> methodAccess.invoke(instance, indexGet) }
}

/**
 * Sets up the property to use ReflectASM.
 */
private fun KMutableProperty1<*, *>.fastSetup(kClass: KClass<*>) {
    val methodAccess = kClass.reflectAsmMethodAccess
    val getMethodName = ("get" + this.name.capitalize()).takeIf { it in methodAccess.methodNames }
            ?: ("is" + this.name.capitalize()).takeIf { it in methodAccess.methodNames }
    val indexGet = methodAccess.getIndex(getMethodName)
    val indexSet = methodAccess.getIndex("set" + this.name.capitalize())
    KProperty1ReflectAsmGet.putIfAbsent(this) { instance: Any -> methodAccess.invoke(instance, indexGet) }
    KProperty1ReflectAsmSet.putIfAbsent(this) { instance: Any, value: Any? -> methodAccess.invoke(instance, indexSet, value) }
}

private val KProperty1FastType = HashMap<KProperty1<*, *>, TypeInformation>()
/**
 * Gets the [TypeInformation] of the return type.
 */
val KProperty1<*, *>.fastType get() = KProperty1FastType.getOrPut(this) { TypeInformation(this.returnType, this.annotations) }


private val KFunctionReflectAsmInvoke = HashMap<KFunction<*>, (Any, Array<out Any?>) -> Unit>()
/**
 * Invokes the function using ReflectASM.
 */
fun KFunction<*>.reflectAsmInvoke(instance: Any, vararg args: Any?): Any? {
    val quick = KFunctionReflectAsmInvoke[this]
    return if(quick != null) quick.invoke(instance, args)
    else this.call(instance, *args)
}

/**
 * Sets up the function to use ReflectASM.
 */
private fun KFunction<*>.fastSetup(kClass: KClass<*>) {
    try {
        val methodAccess = kClass.reflectAsmMethodAccess
        val index = methodAccess.getIndex(this.name, this.parameters.size - 1)
        KFunctionReflectAsmInvoke[this] = { instance, params -> methodAccess.invoke(instance, index, *params) }
    } catch(e:Exception){
        e.printStackTrace()
    }
}


/**
 * Constructs an instance of this class using ReflectASM.
 */
fun <T : Any> KClass<T>.reflectAsmConstruct(): T = reflectAsmConstructorAccess.newInstance()