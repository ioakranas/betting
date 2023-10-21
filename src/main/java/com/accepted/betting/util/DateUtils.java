package com.accepted.betting.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateUtils {

	public static boolean isInFuture(LocalDate date, LocalTime time) {
		return date.atTime(time).isAfter(LocalDateTime.now());
	}
}
