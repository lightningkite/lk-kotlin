//package lk.kotlin.json
//
//import com.fasterxml.jackson.core.JsonGenerator
//import com.fasterxml.jackson.core.JsonParseException
//import com.fasterxml.jackson.core.JsonParser
//import com.fasterxml.jackson.core.JsonToken
//import lk.kotlin.reflect.TypeInformation
//import lk.kotlin.reflect.closest
//import java.io.InputStream
//import java.io.OutputStream
//import kotlin.reflect.KClass
//
//interface JsonTypeHandler<T>{
//    fun parse(typeInformation: TypeInformation, parser: JsonParser):T
//    fun render(typeInformation: TypeInformation, value:T, generator:JsonGenerator)
//}
//
//class Json:JsonTypeHandler {
//
//    val subHandlers = HashMap<KClass<*>, JsonTypeHandler>()
//    val handlerGenerators = ArrayList<(KClass<*>)->JsonTypeHandler?>()
//
//    fun getSubHandler(kClass: KClass<*>) = subHandlers.getOrPut(kClass){
//        handlerGenerators.asSequence().mapNotNull { it.invoke(kClass) }.first()
//    }
//
//    override fun <T> parse(typeInformation: TypeInformation, parser: JsonParser): T {
//        return subHandlers[typeInformation.kclass]!!.parse(typeInformation, parser)
//    }
//
//    override fun <T> render(typeInformation: TypeInformation, value: T, generator: JsonGenerator) {
//        val sub = subHandlers.getOrPut(typeInformation.kclass){
//            subHandlers[subHandlers.keys.closest(typeInformation.kclass)]!!
//        }
//        return sub.render(typeInformation, value, generator)
//    }
//
//    object BooleanHandler: JsonTypeHandler<Boolean>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Boolean = parser.nextBooleanValue()
//        override fun render(typeInformation: TypeInformation, value: Boolean, generator: JsonGenerator) {
//            generator.writeBoolean(value)
//        }
//    }
//
//    object ByteHandler: JsonTypeHandler<Byte>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Byte {
//            val result = parser.byteValue
//            parser.nextToken()
//            return result
//        }
//        override fun render(typeInformation: TypeInformation, value: Byte, generator: JsonGenerator) {
//            generator.writeNumber(value.toShort())
//        }
//    }
//
//    object ShortHandler: JsonTypeHandler<Short>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Short {
//            parser.nextToken()
//            parser.shortValue
//        }
//        override fun render(typeInformation: TypeInformation, value: Short, generator: JsonGenerator) {
//            generator.writeNumber(value)
//        }
//    }
//
//    object IntHandler: JsonTypeHandler<Int>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Int = parser.nextIntValue(0)
//        override fun render(typeInformation: TypeInformation, value: Int, generator: JsonGenerator) {
//            generator.writeNumber(value)
//        }
//    }
//
//    object LongHandler: JsonTypeHandler<Long>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Long = parser.nextLongValue(0)
//        override fun render(typeInformation: TypeInformation, value: Long, generator: JsonGenerator) {
//            generator.writeNumber(value)
//        }
//    }
//
//    object ByteArrayHandler: JsonTypeHandler<ByteArray>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): ByteArray {
//            parser.nextToken()
//            return parser.binaryValue
//        }
//        override fun render(typeInformation: TypeInformation, value: ByteArray, generator: JsonGenerator) {
//            generator.writeBinary(value)
//        }
//    }
//
//    abstract class ArrayHandler: JsonTypeHandler<Array<*>>{
//        override fun parse(typeInformation: TypeInformation, parser: JsonParser): Array<*> {
//            val subtype = typeInformation.typeParameters.first()
//            var result = ArrayList<Any?>()
//            parser.nextToken()
//            if(!parser.isExpectedStartArrayToken) throw JsonParseException(parser, "Expected array, was another type")
//            while(true){
//                parser.nextToken()
//                if(parser.currentToken == JsonToken.END_ARRAY) break
//                p
//            }
//        }
//
//        override fun render(typeInformation: TypeInformation, value: Array<*>, generator: JsonGenerator) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }
//}