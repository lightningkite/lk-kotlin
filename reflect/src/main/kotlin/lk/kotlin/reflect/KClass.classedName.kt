package lk.kotlin.reflect

import kotlin.reflect.KClass


val KClass<*>.classedName:String? get() {
    val startPos = qualifiedName?.indexOfFirst { it.isUpperCase() }?.takeIf { it != -1 }
    if(startPos != null){
        return qualifiedName!!.substring(startPos)
    } else{
        return null
    }
}