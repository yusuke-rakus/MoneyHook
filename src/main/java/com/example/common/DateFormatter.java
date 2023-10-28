package com.example.common;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class DateFormatter {

	public static Date toFirstDayOfMonth(Date date) {
		if (Objects.isNull(date)) {
			return null;
		}

		LocalDate ld = date.toLocalDate();
		Date d = Date.valueOf(ld.getYear() + "-" + ld.getMonthValue() + "-" + 1);
		return d;
	}
}
