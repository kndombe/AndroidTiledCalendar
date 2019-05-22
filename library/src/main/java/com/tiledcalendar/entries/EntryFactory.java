package com.tiledcalendar.entries;

import java.util.Calendar;

import androidx.annotation.NonNull;

/**
 * Provides methods necessary to perform certain tasks on different <code>Entry</code> objects.
 */
public final class EntryFactory {

    public static Entry makeEntry(
            @NonNull final String uniqueID,
            @NonNull final String name,
            final long startDateTime,
            final long endDateTime,
            final int color
    ) {
        return new Entry() {
            @Override
            public String getUniqueID() {
                return uniqueID;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public long getStartDateTime() {
                return startDateTime;
            }

            @Override
            public long getEndDateTime() {
                return endDateTime;
            }

            @Override
            public int getColor() {
                return color;
            }
        };
    }

    /**
     * Creates an instance of a class according to the <code>Entry</code> interface to remove the
     * necessity of creating a class that implements from it.
     * This method uses the parameters to assign the corresponding values to the created instance.
     * @param uniqueID the unique ID of the created <code>Entry</code>.
     * @param name the name that should be associated with the created <code>Entry</code>.
     * @param startDateTime the start time corresponding to the created <code>Entry</code>.
     * @param endDateTime the end time corresponding to the created <code>Entry</code>.
     * @param color the color that should be associated with the created <code>Entry</code>.
     * @return an object instance implementing <code>Entry</code> with the given parameters.
     */
    public static Entry makeEntry(
            @NonNull String uniqueID,
            @NonNull String name,
            Calendar startDateTime,
            Calendar endDateTime,
            int color
    ) {
        return makeEntry(
                uniqueID,
                name,
                startDateTime.getTimeInMillis(),
                endDateTime.getTimeInMillis(),
                color);
    }
}
