package lk.kotlin.jvm.utils.exception

import java.io.PrintWriter
import java.io.StringWriter

fun Exception.stackTraceString():String{
    val writer = StringWriter()
    printStackTrace(PrintWriter(writer))
    return writer.toString()
}