package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class KmpYearMonth(val year: Int, val month: Int) {

    companion object {
        fun now(): KmpYearMonth {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            return KmpYearMonth(currentDate.year, currentDate.monthNumber)
        }

        fun of(year: Int, month: Int): KmpYearMonth {
            return KmpYearMonth(year, month)
        }
    }

    fun atDay(day: Int): LocalDate {
        return LocalDate(year, month, day)
    }

    fun getMonthName(): String {
        return Month.entries[month - 1].name
    }


    fun lengthOfMonth(year: Int, month: Int): Int {
        var currentDay = LocalDate(year, month, 1)
        var count = 0

        while (currentDay.monthNumber == month) {
            currentDay = currentDay.plus(1, DateTimeUnit.DAY)
            count++
        }

        return count
    }

    fun plusMonth(value: Int): KmpYearMonth {
        val nextMonthDate = LocalDate(year, month, 1).plus(value, DateTimeUnit.MONTH)
        return KmpYearMonth(nextMonthDate.year, nextMonthDate.monthNumber)
    }

    fun minusMonth(value: Int): KmpYearMonth {
        val previousMonthDate = LocalDate(year, month, 1).minus(value, DateTimeUnit.MONTH)
        return KmpYearMonth(previousMonthDate.year, previousMonthDate.monthNumber)
    }
}

fun KmpYearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate(year, month, 1)
    val firstDayOfNextMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH)
val firstDayShow = firstDayOfMonth.minus( firstDayOfMonth.dayOfWeek.ordinal ,DateTimeUnit.DAY)
    return generateSequence(firstDayShow) { it.plus(1, DateTimeUnit.DAY) }
        .takeWhile { it < firstDayOfNextMonth }
        .toList()
}

fun getFirstMondayOfMonth(firstDayOfMonth:LocalDate): LocalDate {
    var firstMondayOfMonth = firstDayOfMonth
    while (firstMondayOfMonth.dayOfWeek != DayOfWeek.MONDAY) {
        firstMondayOfMonth = firstMondayOfMonth.plus(1, DateTimeUnit.DAY)
    }
    return firstMondayOfMonth
}

object DateUtil {

    val daysOfWeek: Array<String>
        get() {
            val daysOfWeek = Array(7) { "" }

            for (dayOfWeek in DayOfWeek.entries) {
                val localizedDayName = dayOfWeek.name
                daysOfWeek[dayOfWeek.isoDayNumber - 1] = localizedDayName
            }

            return daysOfWeek
        }
}


