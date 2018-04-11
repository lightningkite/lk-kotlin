package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.stream.toByteArray
import lk.kotlin.jvm.utils.stream.toFile
import lk.kotlin.jvm.utils.stream.toString
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.File
import kotlin.test.assertEquals

class StreamTest {

    @Test
    fun testToByteArray() {
        val original = "test".toByteArray()
        val stream = ByteArrayInputStream(original)
        assert(stream.toByteArray() contentEquals original)
    }

    @Test
    fun testToString() {
        val original = "test"
        val stream = ByteArrayInputStream(original.toByteArray())
        assertEquals(original, stream.toString(charset = Charsets.UTF_8))
    }

    @Test
    fun testToFile() {
        val tempFile = File.createTempFile("test", "txt")
        tempFile.createNewFile()

        val original = "test"
        val stream = ByteArrayInputStream(original.toByteArray())
        stream.toFile(tempFile)

        assertEquals(original, tempFile.readText())

        tempFile.delete()
    }
}