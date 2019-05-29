package com.tiledcalendar;

import android.graphics.Color;
import android.os.Bundle;

import com.tiledcalendar.common.Utils;
import com.tiledcalendar.entries.EntryFactory;
import com.tiledcalendar.tiledmonthview.OnTiledMonthEventListener;
import com.tiledcalendar.tiledmonthview.TiledMonthCalendar;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TiledCalendarExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiled_calendar_example);

        // Init the calendar
        TiledMonthCalendar tiledMonth = findViewById(R.id.tiled_month_view);

        // Add entries to the calendar.
        tiledMonth.addEntries(Arrays.asList(
                EntryFactory.makeEntry(
                        "ThisCouldLiterallyBeWhatever",
                        "Car Appointment",
                        new GregorianCalendar(
                                2019, 3 /* April */, 20).getTimeInMillis(),
                        new GregorianCalendar(
                                2019, 3 /* April */, 22).getTimeInMillis(),
                        Color.GRAY),
                EntryFactory.makeEntry(
                        "qwerty",
                        "Dinner with the guys at Cherry's",
                        new GregorianCalendar(2019, 4 /* May */, 17, 17, 30),
                        new GregorianCalendar(2019, 4 /* May */, 17, 20, 0),
                        Color.BLUE),
                EntryFactory.makeEntry(
                        "mcmvncnx",
                        "Spans through 3 days",
                        new GregorianCalendar(2019, 4, 18),
                        new GregorianCalendar(2019, 4, 20),
                        Color.GREEN),
                EntryFactory.makeEntry(
                        "aasdlkfjdklf",
                        "This is an event that spans through 2 days and that has a pretty lengthy text",
                        new GregorianCalendar(2019, 4 /* May */, 19),
                        new GregorianCalendar(2019, 4 /* May */, 20),
                        Color.rgb(100, 150, 120)),
                EntryFactory.makeEntry(
                        "adf",
                        "Massage",
                        new GregorianCalendar(
                                2019, 5, 10).getTimeInMillis(),
                        new GregorianCalendar(
                                2019, 5, 10).getTimeInMillis(),
                        Color.RED)
        ));

        // Set Dark Mode
        tiledMonth.activateDarkMode();

        // Set custom colors
        // If you leave an argument to null, the current color for that field will be used.
        tiledMonth.setCustomThemeColors(
                Color.rgb(54, 59, 99) /* background */ ,
                null /* foreground */,
                null /* selectedDateCell */,
                null /* currentDateCell */,
                null /* currentDateCellText */,
                Color.rgb(102, 56, 97) /* weekdayBackground */,
                null /* weekdayForeground */,
                null /* buttonForeground */);

        // Listen to events
        tiledMonth.setTiledMonthEventListener(new OnTiledMonthEventListener() {
            @Override
            public void onCellClick(
                    long date,
                    List<String> tileIDs,
                    boolean selectedDateChanged,
                    boolean monthChanged) {
                // do something
            }

            @Override
            public void onTileClick(long date, String id) {
                // do something
            }

            @Override
            public void onSwipe(boolean monthChanged, long date) {
                // do something
            }

            @Override
            public void onMonthChanged(long date) {
                // do something
            }
        });
    }
}
