package lk.kotlin.reflect

import lk.kotlin.reflect.annotations.externalName
import kotlin.reflect.KClass

object ExternalClassRegistry {
    private val reverseMap = HashMap<KClass<*>, String>()
    private val map = HashMap<String, KClass<*>>()
    fun register(kclass: KClass<*>, asName: String = kclass.externalName) {
        val prev = map.put(asName, kclass)
        if(prev != null && prev != kclass)
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

    init{
        register(Boolean::class)

        register(Byte::class)
        register(Short::class)
        register(Int::class)
        register(Long::class)

        register(Char::class)
        register(String::class)

        register(List::class)
        register(Map::class)
        register(Set::class)
    }
}