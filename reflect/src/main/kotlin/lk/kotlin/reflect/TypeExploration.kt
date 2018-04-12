package lk.kotlin.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

object TypeExploration {
    fun explore(
            add: (KClass<*>) -> Boolean,
            type: KClass<*>
    ) {
        if (!add(type)) return
        for (prop in type.fastMutableProperties) {
            explore(add, prop.value.returnType)
        }
        for (func in type.fastFunctions) {
            explore(add, func.returnType)
            for (param in func.parameters) {
                explore(add, param.type)
            }
        }
    }

    fun explore(
            add: (KClass<*>) -> Boolean,
            type: KType
    ) {
        explore(add, type.jvmErasure)
        for (sub in type.arguments) {
            val subType = sub.type
            if (subType != null) {
                explore(add, subType)
            }
        }
    }
}

