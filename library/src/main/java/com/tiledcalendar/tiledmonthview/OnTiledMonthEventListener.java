package com.tiledcalendar.tiledmonthview;

import java.util.List;

public interface OnTiledMonthEventListener {

    /**
     * Fired when a cell in the month view registers a click.
     * @param date the date of the clicked cell.
     * @param tileIDs a list of the IDs associated with the tiles for the corresponding date.
     * @param selectedDateChanged specifies if the newly selected date is different the old one.
     * @param monthChanged specifies if the newly selected date is in a different month than the old
     *                     selected date.
     */
    void onCellClick(
            long date, List<String> tileIDs, boolean selectedDateChanged, boolean monthChanged);

    /**
     * Fired whenever a tile is clicked from the Month Cell Dialog window.
     * @param date the date that the selected tile belongs to.
     * @param id the ID associated with the selected tile.
     */
    void onTileClick(long date, String id);

    /**
     * Fired when the grid view is swiped right or left. That includes swipes that end up in a month
     * change as well as milder swipes that do not cause the month to change.
     * This fires only after the swipe has occurred, not during.
     * @param monthChanged whether the month has changed after the swipe.
     * @param date the new selected date after the swipe.
     */
    void onSwipe(boolean monthChanged, long date);

    /**
     * Fired whenever the current month is changed either through swipe or programmatically.
     * @param date the new selected date after the swipe.
     */
    void onMonthChanged(long date);
}
