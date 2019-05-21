package com.tiledcalendar.tiledmonthview;

import com.tiledcalendar.entries.Entry;

import java.util.List;

import androidx.annotation.NonNull;

public interface TiledMonth <T extends Entry> {
    /**
     * Gets the time in milliseconds of the current date in the local time zone and locale.
     * The returned time is set to midnight of the corresponding date.
     * @return a date in milliseconds
     */
    long getCurrentDate();

    /**
     * Gets the time in milliseconds of the currently selected date on the calendar.
     * The returned time is set to midnight of the corresponding date.
     * @return a date in milliseconds.
     */
    long getCurrentSelected();

    /**
     * Gets the time in milliseconds of the date of the first visible cell in the calendar.
     * The returned time is set to midnight of the corresponding date.
     * @return a date in milliseconds.
     */
    long getFirstVisibleDate();

    /**
     * Gets the time in milliseconds of the date of the last visible cell in the calendar.
     * The returned time is set to midnight of the corresponding date.
     * @return a date in milliseconds.
     */
    long getLastVisibleDate();

    /**
     * Add a list of entries to the calendar. This list will be used to populate the cells with
     * tiles that correspond to the entries in this list.
     * @param newEntries the list of entries to be displayed.
     */
    void addEntries(List<T> newEntries);

    /**
     * Set the event listener to intercept events coming from this calendar.
     * @param listener an instance of <code>OnTiledMonthEventListener</code>.
     */
    void setTiledMonthEventListener(@NonNull OnTiledMonthEventListener listener);
}
