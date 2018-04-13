package lk.kotlin.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.jvm.jvmErasure

object TypeExploration {
    fun explore(
            add: (KClass<*>) -> Boolean,
            type: KClass<*>
    ) {
        if (!add(type)) return
        if (type.fastAllSuperclasses.contains(Enum::class)) return
        for (prop in type.fastMutableProperties) {
            if (prop.value.visibility != KVisibility.PUBLIC) continue
            explore(add, prop.value.returnType)
        }
        for (func in type.fastFunctions) {
            if (func.visibility != KVisibility.PUBLIC) continue
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

