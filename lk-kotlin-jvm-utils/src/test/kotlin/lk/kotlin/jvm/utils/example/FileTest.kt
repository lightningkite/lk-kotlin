package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.files.child
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class FileTest {

    @Test
    fun testChild() {
        val parent = File(".")
        assertEquals(File(parent, "child"), parent.child("child"))
    }
}