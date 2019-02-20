package net.telepathix.petbase.helper

import java.util.*

val workingHoursRegex =
    "\\s*(M|T|W|R|F|S|U|Mo|Tu|We|Th|Fr|Sa|Su|Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s*-\\s*(M|T|W|R|F|S|U|Mo|Tu|We|Th|Fr|Sa|Su|Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s*(\\d\\d?):(\\d\\d)\\s*-\\s*(\\d\\d?):(\\d\\d)\\s*"
        .toRegex(setOf(RegexOption.IGNORE_CASE))

// use this for values Calendar.MONDAY, Calendar.TUESADAY etc.
typealias CalendarDay = Int

const val MINUTES_PER_HOUR = 60
const val DAYS_PER_WEEK = 60

enum class Weekday(
    val oneLetterAbbreviation: String,
    val twoLetterAbbreviation: String,
    val threeLetterAbbreviation: String,
    val calendarValue: CalendarDay
) {
    Monday("M", "Mo", "Mon", Calendar.MONDAY),
    Tuesday("T", "Tu", "Tue", Calendar.TUESDAY),
    Wednesday("W", "We", "Wed", Calendar.WEDNESDAY),
    Thursday("R", "Th", "Thu", Calendar.THURSDAY),
    Friday("F", "Fr", "Fri", Calendar.FRIDAY),
    Saturday("S", "Sa", "Sat", Calendar.SATURDAY),
    Sunday("U", "Su", "Sun", Calendar.SUNDAY);

    companion object {
        fun fromAbbreviation(abbr: String) : Weekday? {
            for (weekday in Weekday.values()) {
                if (weekday.oneLetterAbbreviation == abbr
                    || weekday.twoLetterAbbreviation == abbr
                    || weekday.threeLetterAbbreviation == abbr) {
                    return weekday
                }
            }
            return null
        }
    }
}

data class OpeningHours (
    val startDay: Weekday,
    val endDay: Weekday,
    val startMinutesPastMidnight: Int,
    val endMinutesPastMidnight: Int
) {
    fun isOpenNow(): Boolean {
        val cal = Calendar.getInstance()
        return isOpenAt(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
    }

    fun isOpenAt(day: CalendarDay, hour: Int, minutes: Int) =
        isDayInRange(day, startDay.calendarValue, endDay.calendarValue)
                && startMinutesPastMidnight <= makeMinutesPastMidnight(hour, minutes)
                && makeMinutesPastMidnight(hour, minutes) <= endMinutesPastMidnight

    fun isDayInRange(day: CalendarDay, startRange: CalendarDay, endRange: CalendarDay)
            = startRange <= adjustDay(day, startRange) && adjustDay(day, startRange) <= adjustDay(endRange, startRange)

    fun adjustDay(day: CalendarDay, startDay: CalendarDay) = if (day < startDay) day + DAYS_PER_WEEK else day

    companion object {
        private val neverOpen = OpeningHours(Weekday.Monday, Weekday.Monday, 0, 0)
        fun makeMinutesPastMidnight(hours: Int, minutes: Int) = hours * MINUTES_PER_HOUR + minutes
        fun makeMinutesPastMidnight(hours: String, minutes: String) = makeMinutesPastMidnight(hours.toInt(), minutes.toInt())

        fun parseWorkHours(workHours: String) =
            workingHoursRegex.matchEntire(workHours)?.let {
                val startDay = Weekday.fromAbbreviation(it.groupValues[1])
                val endDay = Weekday.fromAbbreviation(it.groupValues[2])
                val startTime = makeMinutesPastMidnight(it.groupValues[3], it.groupValues[4])
                val endTime = makeMinutesPastMidnight(it.groupValues[5], it.groupValues[6])
                if (startDay != null && endDay != null && endTime > startTime)
                    OpeningHours(startDay, endDay, startTime, endTime) else neverOpen
            } ?: neverOpen
    }
}
