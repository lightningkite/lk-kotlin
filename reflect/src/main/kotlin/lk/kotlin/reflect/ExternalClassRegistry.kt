package lk.kotlin.reflect

import lk.kotlin.reflect.annotations.externalName
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

object ExternalClassRegistry {

    private val reverseMapJava = HashMap<Class<*>, String?>()
    private val reverseMap = HashMap<KClass<*>, String?>()
    private val map = HashMap<String, KClass<*>>()

    val types: Map<String, KClass<*>> get() = map

    fun register(kclass: KClass<*>, asName: String = kclass.externalName) {
        val prev = map.put(asName, kclass)
        reverseMap.put(kclass, asName)
        reverseMapJava.put(kclass.java, asName)
        if(prev != null && !kclass.isSubclassOf(prev))
            throw IllegalArgumentException("Type already registered to name ${kclass.externalName}!")
    }

    fun tryRegister(kclass: KClass<*>, asName: String = kclass.externalName): Boolean {
        return if (map.containsKey(asName)) {
            false
        } else {
            register(kclass)
            true
        }
    }

    fun registerWithSubtypes(kclass: KClass<*>) {
        TypeExploration.explore(
                { tryRegister(it) },
                kclass
        )
    }
    operator fun get(name:String):KClass<*>? = map[name]
    operator fun get(type: KClass<*>): String? = reverseMap.getOrPut(type){
        //Go through ancestors
        type.fastAllSuperclasses.asSequence().mapNotNull { reverseMap[it] }.firstOrNull()
    }
    operator fun get(type: Class<*>): String? = reverseMapJava.getOrPut(type){
        //Go through ancestors
        type.kotlin.fastAllSuperclasses.asSequence().mapNotNull { reverseMap[it] }.firstOrNull()
    }

    init{
        register(Any::class)

        register(Boolean::class)

        register(Byte::class)
        register(Short::class)
        register(Int::class)
        register(Long::class)

        register(Char::class)
        register(String::class)

        register(Date::class)

        register(List::class)
        register(Map::class)
        register(Set::class)
    }
}