package com.tiledcalendar.entries;

/**
 * Provides the exhaustive list of methods that will be needed from any class implementing the
 * <code>Entry</code> interface.
 */
public interface Entry {

    /**
     * Gets the unique identifier associated with an instance of the class implementing this
     * interface.
     * If that class already has some form of unique ID, this method
     * could simply return that ID as a <code>String</code>. Otherwise, it is necessary to come up
     * with a way to identify each instance of the class uniquely and return that identifier from
     * this method.
     *
     * Note: The unique ID is used in telling apart the different calendar entries that are passed
     * to this calendar. It is also used to know with accuracy which <code>Entry</code> is the
     * target of a specific event action.
     * If 2 <code>Entry</code> objects return the same value for <code>Entry#getUniqueID</code>, the
     * latest one added to the calendar will overwrite the one that was added before.
     * @return the unique identifier as a <code>String</code>
     */
    String getUniqueID();

    /**
     * Gets the name associated with this <code>Entry</code>.
     * This will be used to display the tile in
     * the calendar.
     * @return a <code>String</code> corresponding to the name.
     */
    String getName();

    /**
     * Gets the time in milliseconds representing when this <code>Entry</code> instance is supposed
     * to start.
     * This will be used to determine where to start displaying the tiles for this
     * <code>Entry</code> in the calendar.
     *
     * Note: for an <code>Entry</code> without any duration, you could simply use the same value for
     * <code>Entry#getStartDateTime</code> and <code>Entry#getEndDateTime</code>.
     * @return the start time in milliseconds.
     */
    long getStartDateTime();

    /**
     * Gets the time in milliseconds representing when this <code>Entry</code> instance is supposed
     * to end.
     * This will be used to determine where to stop displaying the tiles for this
     * <code>Entry</code> in the calendar.
     *
     * Note: for an <code>Entry</code> without any duration, you could simply use the same value for
     * <code>Entry#getStartDateTime</code> and <code>Entry#getEndDateTime</code>.
     * @return the end time in milliseconds.
     */
    long getEndDateTime();

    /**
     * Gets the color that should be used to display the tile corresponding to this
     * <code>Entry</code>.
     * @return an integer corresponding to a <code>Color</code>.
     */
    int getColor();
}