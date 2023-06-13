package com.team.temara.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(currentDateString: String?, targetTimeZone: String): String {
        val instant= Instant.parse(currentDateString)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.of(targetTimeZone))
        return formatter.format(instant)
    }
}