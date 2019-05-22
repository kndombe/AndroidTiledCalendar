package com.tiledcalendar.tiledmonthview;

import com.tiledcalendar.entries.Entry;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * A month view calendar that allows to customize each month cell by adding tiles
 * with a custom text.
 *
 * The user of this interface has the possibility to add an <code>Entry</code> or list
 * <code>List</code> of <code>Entry</code>s that will be used in displaying the <code>Tile</code>s
 * in the month view.
 *
 * Since <code>Entry</code> is an interface, there is a need to have a class that implements the
 * <code>Entry</code> interface and overrides all necessary methods. The user of the interface has
 * the choice to use a custom class that does those things.
 * However, the user also has the choice to use the <code>EntryFactory#makeEntry</code> method to
 * directly create an instance that they can store in a variable of type <code>Entry</code>, without
 * needing to create a custom class implementing <code>Entry</code>.
 * e.g. <code>Entry entry = EntryFactory.makeEntry(...)</code>
 *
 * The tiles will be displayed from top to bottom following 3 rules of comparisons: 1) which
 * <code>Entry</code> has the earliest <code>Entry#getStartDateTime</code>, unless these are equal,
 * then 2) which <code>Entry</code> has the earliest <code>Entry#getEndDateTime</code>, unless these
 * are equal, then 3) which <code>Entry</code> has the smallest <code>Entry#getName</code>
 * lexicographically.
 * Depending on the height available for the view, each cell will only contain up to a certain
 * number of visible <code>Tile</code>s. If there's more available <code>Tile</code>s, those will be
 * present but simply not fully visible.
 *
 * @author Kelly Ndombe
 */
public interface TiledMonthCalendar {

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
     * Add an <code>Entry</code> object to the calendar. This object will be added to the list of
     * <code>Entry</code> objects used to populate the calendar cells with corresponding tiles.
     * If this <code>Entry</code> has the same <code>Entry#getUniqueID</code> value as an
     * <code>Entry</code> that was added before, this <code>Entry</code> will overwrite the old
     * one.
     * @param newEntry the <code>Entry</code> that is being added.
     */
    void addEntry(Entry newEntry);

    /**
     * Add a list of <code>Entry</code> objects to the calendar. This list will be used to populate
     * the calendar cells with tiles that correspond to the entries in this list.
     * If an <code>Entry</code> that is being added has the same <code>Entry#getUniqueID</code>
     * value as an <code>Entry</code> that was added before, the new <code>Entry</code> will
     * overwrite the old one.
     * @param newEntries the list of <code>Entry</code> objects to be displayed.
     */
    void addEntries(List<Entry> newEntries);

    /**
     * Set the event listener to intercept events coming from this calendar.
     * @param listener an instance of <code>OnTiledMonthEventListener</code>.
     */
    void setTiledMonthEventListener(@NonNull OnTiledMonthEventListener listener);
}
