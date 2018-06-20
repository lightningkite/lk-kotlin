package lk.kotlin.jvm.utils.exception

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Returns the stack trace as a string.
 */
fun Exception.stackTraceString():String{
    val writer = StringWriter()
    printStackTrace(PrintWriter(writer))
    return writer.toString()
}