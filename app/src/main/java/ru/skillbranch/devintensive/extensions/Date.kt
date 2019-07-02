package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        else -> throw IllegalStateException("invalid unit")
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
/*
    0с - 1с "только что"
    1с - 45с "несколько секунд назад"
    45с - 75с "минуту назад"
    75с - 45мин "N минут назад"
    45мин - 75мин "час назад"
    75мин 22ч "N часов назад"
    22ч - 26ч "день назад"
    26ч - 360д "N дней назад"
    >360д "более года назад"
*/
    val diff = abs((date.time - this.time) / 1000).toInt()
    val past = date.time > this.time

    return when (diff) {
        in 0..1 -> "только что" //0s - 1s
        in 2 until 45 -> getCaseTimes(diff, "s", past) //2s - 44s
        in 45 until 75 -> "минуту назад" //45s - 74s
        in 75 until 2700 -> getCaseTimes(diff, "m", past) // 75s - 44m59s
        in 2700 until 4500 -> "час назад" //45m01s - 74m59s
        in 4500 until 79200 -> getCaseTimes(diff, "h", past) //75m01s - 21h59m59s
        in 79200 until 93600 -> "день назад" //22h00m01s - 25h59m59s
        in 93600 until 31104000 -> getCaseTimes(diff, "d", past) //26h00m00s - 360d
        else -> when (past) {
            true -> "более года назад"
            false -> "более чем через год"
        }
    }
}

fun getCaseTimes(value: Int, unit: String, past: Boolean): String {
    var pre = ""
    var post = ""
    if (past) pre = "через " else post = " назад"

    val strTime = when (unit) {
        "s" -> "$value ${getCases(value, unit)}"
        "m" -> "${value / 60} ${getCases(value / 60, unit)}"
        "h" -> "${value / 3600} ${getCases(value / 3600, unit)}"
        "d" -> "${value / 86400} ${getCases(value / 86400, unit)}"
        else -> ""
    }
    return "$pre$strTime$post"
}

fun getCases(value: Int, unit: String): String {
    return when (value) {
        1 -> when (unit) {
            "s" -> TimeCases.SECOND.case1
            "m" -> TimeCases.MINUTE.case1
            "h" -> TimeCases.HOUR.case1
            "d" -> TimeCases.DAY.case1
            else -> ""
        }
        in 2..4 -> when (unit) {
            "s" -> TimeCases.SECOND.case2
            "m" -> TimeCases.MINUTE.case2
            "h" -> TimeCases.HOUR.case2
            "d" -> TimeCases.DAY.case2
            else -> ""
        }
        0, in 5..14 -> when (unit) {
            "s" -> TimeCases.SECOND.case3
            "m" -> TimeCases.MINUTE.case3
            "h" -> TimeCases.HOUR.case3
            "d" -> TimeCases.DAY.case3
            else -> ""
        }
        else -> getCases(value % 10, unit)
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

enum class TimeCases(val case1: String, val case2: String, val case3: String) {
    SECOND("секунду", "секунды", "секунд"),
    MINUTE("минуту", "минуты", "минут"),
    HOUR("час", "часа", "часов"),
    DAY("день", "дня", "дней")
}
