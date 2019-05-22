package com.tiledcalendar.common;

import com.tiledcalendar.entries.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class DateTime {
    private static final long MILLI_IN_SECOND = 1000;
    private static final long MILLI_IN_MINUTE = MILLI_IN_SECOND * 60;
    private static final long MILLI_IN_HOUR = MILLI_IN_MINUTE * 60;
    private static final long MILLI_IN_DAY = MILLI_IN_HOUR * 24;

    public static Calendar getDate(long millis) {
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(millis);
        return date;
    }

    public static <T extends Entry> boolean occursOnDay(
            @NonNull T entry,
            long date
    ) {
        return isInSameDayAs(entry.getStartDateTime(), date) ||
                isInSameDayAs(entry.getEndDateTime(), date) ||
                (entry.getStartDateTime() <= date && entry.getEndDateTime() >= date);
    }

    public static String getFormattedDate(long millis) {
        Date date = new Date(millis);
        DateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        return format.format(date);
    }

    public static String getFormattedTime(long millis) {
        Date date = new Date(millis);
        DateFormat format = new SimpleDateFormat("h:mm aaa", Locale.US);
        return format.format(date);
    }

    public static long getFirstDayOfMonth(long millis) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    public static long getLastDayOfMonth(long millis) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    public static long getDateStart(long millis) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getDateEnd(long millis) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTimeInMillis();
    }

    public static long getRoundedFuture5Minutes() {
        Calendar cal = new GregorianCalendar();
        int min = 5 - (cal.get(Calendar.MINUTE) % 5);

        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + min);
        return cal.getTimeInMillis();
    }

    public static boolean isInSameMonthAs(long date1, long date2) {
        return isInSameMonthAs(getDate(date1), getDate(date2));
    }

    public static boolean isInSameMonthAs(Calendar date1, Calendar date2) {
        return date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
    }

    public static boolean isInSameDayAs(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isInSameDayAs(long date1, long date2) {
        Calendar cal1 = getDate(date1);
        Calendar cal2 = getDate(date2);
        return isInSameDayAs(cal1, cal2);

    }

    public static Calendar relativeTime(Calendar time, int field, int amount) {
        Calendar newTime = getDate(time.getTimeInMillis());
        newTime.add(field, amount);
        return newTime;
    }

    private DateTime() {throw new AssertionError("Cannot be instantiated.");}


}
