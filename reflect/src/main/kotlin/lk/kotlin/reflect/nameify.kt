package lk.kotlin.reflect


private val addSpaceRegex = Regex("[A-Z0-9]")
fun String.nameify(): String {
    return this.replace(".", " - ").replace(addSpaceRegex) { " " + it.value }.trim().capitalize()
}