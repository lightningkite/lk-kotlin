package lk.kotlin.jvm.utils.examples

import lk.kotlin.jvm.utils.date.*
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class DateTest {

    @Test
    fun testDate() {
        val milliseconds = 1424892731111
        val nowDate = Date(milliseconds)
        val nowCalendar = nowDate.toCalendar()
        assertEquals(2015, nowCalendar.year)
        assertEquals(1, nowCalendar.month)
        assertEquals(4, nowCalendar.weekInMonth)
        assertEquals(25, nowCalendar.dayOfMonth)
        assertEquals(4, nowCalendar.dayOfWeek)
        assertEquals(12, nowCalendar.hourOfDay)
        assertEquals(32, nowCalendar.minute)
        assertEquals(11, nowCalendar.second)
        assertEquals(111, nowCalendar.millisecond)
    }
}