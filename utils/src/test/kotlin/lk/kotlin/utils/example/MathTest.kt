package lk.kotlin.utils.example

import lk.kotlin.utils.math.degreesTo
import lk.kotlin.utils.math.pow
import lk.kotlin.utils.math.sin
import lk.kotlin.utils.math.toIntUnsigned
import org.junit.Test
import kotlin.test.assertEquals

class MathTest {

    @Test
    fun testBasicMath() {
        //Why are we testing these?  They obviously work.
        //BECAUSE WE WANT TO SHOW YOU HOW THEY WORK!

        //What's 4 to the power of 4?
        assertEquals(256.0, 4.0 pow 4.0)

        //Uhh... what's the sine of PI/2?
        assertEquals(1.0, (Math.PI / 2).sin())
    }

    @Test
    fun testAngles() {
        //Angles are actually kinda complicated to compare, but we take care of it for you.  Let us give you examples:

        //The difference between 359 degrees and 0 degrees is 1, not 359.
        assertEquals(1.0, 359.0 degreesTo 0.0)

        //Similarly, the difference between 0 degrees and 359 degrees is -1.
        assertEquals(-1.0, 0.0 degreesTo 359.0)

        //There are normal cases, though.
        assertEquals(90.0, 180.0 degreesTo 270.0)
    }

    @Test
    fun testUnsigned() {
        //If you want your bytes to be converted to ints as if they were unsigned, you must do this.
        assertEquals(0xFA, 0xFA.toByte().toIntUnsigned())
    }
}