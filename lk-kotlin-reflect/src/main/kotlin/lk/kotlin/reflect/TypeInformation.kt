package lk.kotlin.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

//Ser/des
class TypeInformation(
        val kclass: KClass<*>,
        val nullable: Boolean = false,
        val typeParameters: List<TypeInformation> = listOf(),
        val annotations: List<Annotation> = listOf()
) {
    constructor(type: KType, annotations: List<Annotation> = listOf()) : this(
            type.jvmErasure,
            type.isMarkedNullable,
            type.arguments.map { TypeInformation(it.type!!) },
            annotations
    )
}

