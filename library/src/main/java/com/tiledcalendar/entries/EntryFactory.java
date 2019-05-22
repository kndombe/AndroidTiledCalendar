package com.tiledcalendar.entries;

import java.util.Calendar;

import androidx.annotation.NonNull;

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
