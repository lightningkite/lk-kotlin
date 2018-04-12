package lk.kotlin.reflect

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.jvm.jvmErasure

/**
 * Information about the Kotlin type of a parameter or return type without type erasure.
 */
class TypeInformation(
        val kclass: KClass<*>,
        val nullable: Boolean = false,
        val typeParameters: List<TypeInformation> = listOf(),
        override val annotations: List<Annotation> = listOf()
): KAnnotatedElement {
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
            is WildcardType -> TypeInformation.fromType(type.upperBounds.first(), annotations)
            else -> throw IllegalArgumentException()
        }

        fun fromKotlin(type: KType, annotations: List<Annotation> = listOf()): TypeInformation = when (type) {
            is KTypeParameter -> TypeInformation(Any::class)
            else -> TypeInformation(
                    kclass = type.jvmErasure,
                    nullable = type.isMarkedNullable,
                    typeParameters = type.arguments.map {
                        TypeInformation.fromKotlin(it.type!!)
                    }
            )
        }
    }
}

abstract class TypeInformationToken<T>{
    val type = javaClass.genericSuperclass
}
inline fun <reified T: Any> typeInformation():TypeInformation {
    return TypeInformation.fromType(object : TypeInformationToken<T>(){}.type.let { it as ParameterizedType }.actualTypeArguments.first())
}

