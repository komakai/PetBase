package net.telepathix.petbase

import net.telepathix.petbase.helper.CalendarDay
import net.telepathix.petbase.helper.OpeningHours
import net.telepathix.petbase.helper.Weekday
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class TimeHelperTest {

    @Test
    fun testTimeRanges() {
        val openHours = OpeningHours.parseWorkHours("M-F 8:30 - 14:00")
        assertNotNull(openHours)
        assertEquals(openHours.startDay, Weekday.Monday)
        assertEquals(openHours.endDay, Weekday.Friday)
        assertEquals(openHours.startMinutesPastMidnight, 510)
        assertEquals(openHours.endMinutesPastMidnight, 840)
        assertFalse(openHours.isOpenAt(Calendar.MONDAY, 8, 29))
        assertTrue(openHours.isOpenAt(Calendar.MONDAY, 8, 30))
        assertTrue(openHours.isOpenAt(Calendar.FRIDAY, 14, 0))
        assertFalse(openHours.isOpenAt(Calendar.FRIDAY, 14, 1))
        assertFalse(openHours.isOpenAt(Calendar.SATURDAY, 8, 30))
    }

    @Test
    fun testEdgeCaseTimeRanges() {
        val openHours = OpeningHours.parseWorkHours("Thu-Tue 8:30 - 14:00")
        assertNotNull(openHours)
        assertEquals(openHours.startDay, Weekday.Thursday)
        assertEquals(openHours.endDay, Weekday.Tuesday)
        assertTrue(openHours.isOpenAt(Calendar.MONDAY, 12, 30))
        assertFalse(openHours.isOpenAt(Calendar.WEDNESDAY, 12, 30))
    }
}