package lk.kotlin.reflect

import lk.kotlin.reflect.annotations.externalName
import kotlin.reflect.KClass

object ExternalClassRegistry {
    private val map = HashMap<String, KClass<*>>()
    fun register(kclass: KClass<*>){
        val prev = map.put(kclass.externalName, kclass)
        if(prev != null && prev != kclass)
            throw IllegalArgumentException("Type already registered to name ${kclass.externalName}!")
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