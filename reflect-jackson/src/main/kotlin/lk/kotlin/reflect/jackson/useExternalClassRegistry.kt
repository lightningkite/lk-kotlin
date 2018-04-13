package lk.kotlin.reflect.jackson

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
import lk.kotlin.reflect.ExternalClassRegistry

fun ObjectMapper.useExternalClassRegistry() {
    var typer: TypeResolverBuilder<*> = object : ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {

    }
    // we'll always use full class name, when using defaulting
    typer = typer.init(JsonTypeInfo.Id.NAME, object : TypeIdResolver {

        override fun init(baseType: JavaType?) {}

        override fun idFromValue(value: Any?): String = value?.javaClass?.let { ExternalClassRegistry[it] } ?: "Null"

        override fun getDescForKnownTypeIds(): String = ""

        override fun idFromBaseType(): String = "Any"

        override fun idFromValueAndType(value: Any?, suggestedType: Class<*>?): String = idFromValue(value)

        override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.NAME

        override fun typeFromId(context: DatabindContext, id: String?): JavaType = id
                ?.let { ExternalClassRegistry[it] }
                .let { context.constructType(it?.java ?: Any::class.java) }

    })
    typer = typer.inclusion(JsonTypeInfo.As.PROPERTY)
    setDefaultTyping(typer)
}