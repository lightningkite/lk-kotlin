package lk.kotlin.reflect

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

/**
 * Information about the Kotlin type of a parameter or return type without type erasure.
 */
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
    companion object {
        fun fromType(type:Type, annotations: List<Annotation> = listOf()):TypeInformation = when(type){
            is ParameterizedType -> TypeInformation(
                    kclass = (type.rawType as Class<*>).kotlin,
                    typeParameters = type.actualTypeArguments.map {TypeInformation.fromType(it) }
            )
            is Class<*> -> TypeInformation(type.kotlin)
            else -> throw IllegalArgumentException()
        }
    }
}

abstract class TypeInformationToken<T>{
    val type = javaClass.genericSuperclass
}
inline fun <reified T: Any> typeInformation():TypeInformation {
    return TypeInformation.fromType(object : TypeInformationToken<T>(){}.type)
}

