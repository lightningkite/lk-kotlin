package lk.kotlin.reflect.jackson

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
import lk.kotlin.reflect.ExternalClassRegistry

val ObjectMapperExternalClassRegistryResolver = object : TypeIdResolver {

    override fun init(baseType: JavaType?) {}

    override fun idFromValue(value: Any?): String = value?.javaClass?.let { ExternalClassRegistry[it] } ?: throw IllegalArgumentException("Value is type ${value?.javaClass?.name}")

    override fun getDescForKnownTypeIds(): String = ""

    override fun idFromBaseType(): String = "Any"

    override fun idFromValueAndType(value: Any?, suggestedType: Class<*>?): String {
        return value?.javaClass?.let { ExternalClassRegistry[it] } ?: suggestedType?.let{ ExternalClassRegistry[it] } ?: throw IllegalArgumentException("Value is type ${value?.javaClass?.name}")
    }

    override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.NAME

    override fun typeFromId(context: DatabindContext, id: String?): JavaType = id
            ?.let {
                ExternalClassRegistry[it]
            }
            ?.let { context.constructType(it.java) }
            ?: throw IllegalArgumentException("Unknown type '$id'")


}

fun ObjectMapper.useExternalClassRegistry() {
    var typer: TypeResolverBuilder<*> = object : ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {
        override fun useForType(t: JavaType): Boolean {
            if (t.isPrimitive) {
                return false
            }
            if(t.isCollectionLikeType) return false
            return super.useForType(t)
        }
    }
    // we'll always use full class name, when using defaulting
    typer = typer.init(JsonTypeInfo.Id.NAME, ObjectMapperExternalClassRegistryResolver)
    typer = typer.inclusion(JsonTypeInfo.As.PROPERTY)
    setDefaultTyping(typer)
}