package services.core.timeutils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char

private const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"
private const val YEAR_DATE_FORMAT = "yyyy-MM-dd"
private const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
private const val DEFAULT_MONTH_FORMAT = "MM/yyyy"

fun String.parseDate(pattern: String): LocalDate {
    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateFormat = LocalDate.Format {
        byUnicodePattern(pattern)
    }
    return dateFormat.parse(this)
}

fun String.parseDateTime(pattern: String): LocalDateTime {
    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateFormat = LocalDateTime.Format {
        byUnicodePattern(pattern)
    }
    return dateFormat.parse(this)
}

fun String.toComparableDate(pattern: String): Int {
    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateFormat = LocalDate.Format {
        byUnicodePattern(pattern)
    }
    return dateFormat.parse(this).toEpochDays()
}

fun String.toComparableDateTime(pattern: String): Int {
    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateFormat = LocalDateTime.Format {
        byUnicodePattern(pattern)
    }
    return dateFormat.parse(this).date.toEpochDays()
}

fun String.getFormattedDate(
    pattern: String = DEFAULT_DATE_FORMAT,
    newPattern: String = DEFAULT_MONTH_FORMAT
): String {
    val localDate = this.parseDate(pattern = pattern)

    @OptIn(FormatStringsInDatetimeFormats::class)
    val newDateFormat = LocalDate.Format {
        byUnicodePattern(newPattern)
    }
    return newDateFormat.format(localDate)
}

fun String.getFormattedDateTime(
    pattern: String = DEFAULT_DATE_TIME_FORMAT,
    newPattern: String = YEAR_DATE_FORMAT
): String {
    val localDate = this.parseDateTime(pattern = pattern)

    @OptIn(FormatStringsInDatetimeFormats::class)
    val newDateFormat = LocalDateTime.Format {
        byUnicodePattern(newPattern)
    }
    return newDateFormat.format(localDate)
}

fun String.getMonthForGraph(pattern: String = DEFAULT_DATE_FORMAT): String {
    val localDate = this.parseDate(pattern = pattern)
    val fullMonthFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
    }
    return fullMonthFormat.format(localDate)
}

fun String.getShortMonthName(
    pattern: String = DEFAULT_DATE_FORMAT
): String {
    val localDate = this.parseDate(pattern = pattern)
    val shortMonthFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
    }
    return shortMonthFormat.format(localDate)
}
