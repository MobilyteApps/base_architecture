package com.baseappname.app.utils

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
object DateTimeUtils {

    fun String.pstToCurrentTime(): String {
        try {
            val readDate = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
            readDate.timeZone = TimeZone.getTimeZone("GMT-07:00") // missing line
            val date = readDate.parse(this)
//    val writeDate = SimpleDateFormat("YYYY-MM-dd hh:mm:ss aa")
//    val writeDate = SimpleDateFormat("hh:mm:ss aa")
            val writeDate = SimpleDateFormat("hh:mm aa")
            writeDate.timeZone = TimeZone.getDefault()
            return writeDate.format(date)
        } catch (e: ParseException) {
            return ""
        }

    }

    /**
     * fun to convert the SERVER TIME TO DISPLAY FORMAT
     *
     * @param serverFormat server formate value
     * @param displayFormat
     * @return
     */
    fun String.serverToLocalDate(serverFormat: String, displayFormat: String): String {
        val df = SimpleDateFormat(serverFormat, Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date
        try {
            if (df.parse(this) != null) {
                date = df.parse(this)!!
                val outputFormat = SimpleDateFormat(displayFormat, Locale.ENGLISH)
                outputFormat.timeZone = TimeZone.getDefault()
                return outputFormat.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun String.serverToDateWithoutTimezone(serverFormat: String, displayFormat: String): String {
        val df = SimpleDateFormat(serverFormat, Locale.ENGLISH)
        val date: Date
        try {
            if (df.parse(this) != null) {
                date = df.parse(this)!!
                val outputFormat = SimpleDateFormat(displayFormat, Locale.ENGLISH)
                return outputFormat.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun Int.getDisplayDay(): String {
        return when (this) {
            1 -> "Sunday - "
            2 -> "Monday - "
            3 -> "Tuesday - "
            4 -> "Wednesday - "
            5 -> "Thursday - "
            6 -> "Friday - "
            7 -> "Saturday - "
            else -> "Sunday - "
        }
    }

    fun String.getAppointmentHeaderDate(): String {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        val today = Calendar.getInstance(TimeZone.getDefault())

        val date = this.split("-")
        cal.set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())


        // Normal Weekdays
        var day = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DayOfWeek.of(cal.get(Calendar.DAY_OF_WEEK)).name.toLowerCase(Locale.ROOT)
                .capitalize() + " - " + this.stringToDate(Constants.DISPLAY_APPOINTMENT_DATE_FORMAT)
        } else {
            cal.get(Calendar.DAY_OF_WEEK)
                .getDisplayDay() + " - " + this.stringToDate(Constants.DISPLAY_APPOINTMENT_DATE_FORMAT)
        }


        // Days here and there of today
        if (today[Calendar.YEAR] == cal[Calendar.YEAR] &&
            today[Calendar.MONTH] == cal[Calendar.MONTH] &&
            today[Calendar.DATE] - 1 == cal[Calendar.DATE]
        ) {
            day = "Yesterday - " + this.stringToDate(Constants.DISPLAY_APPOINTMENT_DATE_FORMAT)
        } else if (today[Calendar.YEAR] == cal[Calendar.YEAR] &&
            today[Calendar.MONTH] == cal[Calendar.MONTH] &&
            today[Calendar.DATE] == cal[Calendar.DATE]
        ) {
            day = "Today - " + this.stringToDate(Constants.DISPLAY_APPOINTMENT_DATE_FORMAT)
        } else if (today[Calendar.YEAR] == cal[Calendar.YEAR] &&
            today[Calendar.MONTH] == cal[Calendar.MONTH] &&
            today[Calendar.DATE] + 1 == cal[Calendar.DATE]
        ) {
            day = "Tomorrow - " + this.stringToDate(Constants.DISPLAY_APPOINTMENT_DATE_FORMAT)
        }
        return day
    }

    fun String.stringToDate(dateFormat: String): String {
        if (this.isNotEmpty()) {
            // 2020-08-30
            val formatter = SimpleDateFormat(dateFormat)
            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            val date = this.split("-")
            calendar.set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
            return formatter.format(calendar.time)
        } else {
            return ""
        }
    }


    fun Long.milliToDate(dateFormat: String): String {
        return try {
            val formatter = SimpleDateFormat(dateFormat)
            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = this
            formatter.format(calendar.time)
        } catch (e: Exception) {
            ""
        }

    }
}