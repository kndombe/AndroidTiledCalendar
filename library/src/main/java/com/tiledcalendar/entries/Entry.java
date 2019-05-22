package com.tiledcalendar.entries;

public interface Entry {
    String getUniqueID();
    String getName();
    long getStartDateTime();
    long getEndDateTime();
    int getColor();
}