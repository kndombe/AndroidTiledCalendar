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
     * Note that the "unique ID" here does not only refer to a way to tell 2 distinct objects
     * apart. It's possible to have multiple copies of the same <code>Entry</code> (i.e. with the
     * same unique ID) but on completely different object instances.
     * Therefore, it's important to make sure that your unique ID is something more than just the
     * hash code of the object, for instance, and something that can be used and checked across
     * multiple object instances.
     * //TODO: give example?
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
     * #getStartDateTime and #getEndDateTime.
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
     * #getStartDateTime and #getEndDateTime.
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