package com.tiledcalendar.entries;

import android.graphics.Color;

import androidx.annotation.RestrictTo;

/**
 * TODO: documentation
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public final class Tile implements Comparable<Tile>{
    private String uniqueID;
    private String name;
    private long startDateTime;
    private long endDateTime;
    private int color;

    public Tile(String uniqueID, String name, long startDateTime, long endDateTime, int color) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.color = color;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int[] getColors() {
        int red = (color>>16)&0xFF;
        int green = (color>>8)&0xFF;
        int blue = (color)&0xFF;
        return new int[]{
                Color.rgb(red, green, blue),
                Color.argb((int)(.85 * 255.0), red, green, blue)
        };
    }

    @Override
    public int compareTo(Tile other) {
        if (this.startDateTime != other.startDateTime) {
            return (int) (this.startDateTime - other.startDateTime);
        } else if (this.endDateTime != other.endDateTime) {
            return (int) (this.endDateTime - other.endDateTime);
        } else {
            return this.name.toLowerCase().compareTo(other.name.toLowerCase());
        }
    }
}