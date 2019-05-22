package com.tiledcalendar.tiledmonthview;

import com.tiledcalendar.common.Algorithms;
import com.tiledcalendar.entries.Entry;
import com.tiledcalendar.entries.Tile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A single cell in the tiled month view calendar.
 * @param <T> the type of the entries stored by each month cell
 */
final class MonthCell <T extends Entry> {
    private Calendar date;
    private List<T> entries;

    MonthCell(Calendar date) {
        this.date = date;
        this.entries = new ArrayList<>();
    }

    /**
     * TODO: documentation
     * @return
     */
    Calendar getDate() {
        return date;
    }

    /**
     * TODO: Documentation
     * @return
     */
    List<Tile> getTiles() {
        List<Tile> cards = new ArrayList<>();
        for (T entry : entries) {
            cards.add(new Tile(
                    entry.getUniqueID(),
                    entry.getName(),
                    entry.getStartDateTime(),
                    entry.getEndDateTime(),
                    entry.getColor()));
        }

        Algorithms.sortIncreasing(cards);

        return cards;
    }

    List<String> getIDs() {
        List<String> ids = new ArrayList<>();
        for (T entry : entries) {
            ids.add(entry.getUniqueID());
        }

        return ids;
    }

    /**
     *
     * @param entry
     */
    void addEntry(T entry) {
        entries.add(entry);
    }

    /**
     * TODO: Documentation
     */
    void clearEntries() {
        entries.clear();
    }
}
