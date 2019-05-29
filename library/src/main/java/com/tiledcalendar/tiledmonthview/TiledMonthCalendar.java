package com.tiledcalendar.tiledmonthview;

import com.tiledcalendar.entries.Entry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
 * The user of the calendar should use <code>#setTiledMonthEventListener</code> to set a listener
 * for the different actions that would be happening within the calendar like date selection, month
 * change and swipes.
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

    // TODO: support for "setCurrentSelected(long)" and "setCurrentToToday()"

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
    void addEntry(@NonNull  Entry newEntry);

    /**
     * Add a list of <code>Entry</code> objects to the calendar. This list will be used to populate
     * the calendar cells with tiles that correspond to the entries in this list.
     * If an <code>Entry</code> that is being added has the same <code>Entry#getUniqueID</code>
     * value as an <code>Entry</code> that was added before, the new <code>Entry</code> will
     * overwrite the old one.
     * @param newEntries the list of <code>Entry</code> objects to be displayed.
     */
    void addEntries(@NonNull  List<Entry> newEntries);

    /**
     * Set the event listener to intercept events coming from this calendar.
     * @param listener an instance of <code>OnTiledMonthEventListener</code>.
     */
    void setTiledMonthEventListener(@NonNull OnTiledMonthEventListener listener);

    /**
     * Sets the calendar's colors to use the light mode. This is the mode used by default.
     * This will choose a light color to apply to the general backgrounds and a darker color to be
     * applied to the foregrounds.
     */
    void activateLightMode();

    /**
     * Sets the calendar's colors to use the dark mode.
     * This will choose a dark color to apply to the general backgrounds and a lighter color to be
     * applied to the foregrounds.
     */
    void activateDarkMode();

    /**
     * Sets the calendar's colors according to the colors that are passed in parameters.
     * The parameters set to <code>null</code> will result in re-using the currently used color for
     * the concerned elements.
     * @param background color for the general background, i.e. background for month cells, the
     *                   header and the pop-up dialog when a tiled month cell is clicked.
     * @param foreground color for the general foreground, i.e. foreground in the month cells (the
     *                   text color for the adjacent month cells will also be taken from this, with
     *                   a slightly lower alpha value), the 'current month' text in the header and
     *                   the 'selected date' text in the pop-up dialog when a tiled month is
     *                   clicked.
     * @param selectedDateCell color of the cell that is currently selected by the user.
     * @param currentDateCell color of the circle around the date in the cell corresponding to the
     *                        current day
     * @param weekdayBackground color for the background of the weekdays line
     * @param weekdayForeground color for the text in the weekdays line
     * @param buttonForeground color of the next and previous buttons.
     */
    void setCustomThemeColors(
            @Nullable Integer background,
            @Nullable Integer foreground,
            @Nullable Integer selectedDateCell,
            @Nullable Integer currentDateCell,
            @Nullable Integer currentDateCellText,
            @Nullable Integer weekdayBackground,
            @Nullable Integer weekdayForeground,
            @Nullable Integer buttonForeground
            );
}
