package com.tiledcalendar;

import android.graphics.Color;
import android.os.Bundle;

import com.tiledcalendar.entries.Entry;
import com.tiledcalendar.entries.EntryFactory;
import com.tiledcalendar.tiledmonthview.OnTiledMonthEventListener;
import com.tiledcalendar.tiledmonthview.TiledMonth;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public class TiledCalendarTesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiled_calendar_tester);
        TiledMonth<Entry> tiledMonth = findViewById(R.id.tiled_month_view);
        tiledMonth.addEntries(Arrays.asList(
                EntryFactory.makeEntry(
                        "s",
                        "March",
                        new GregorianCalendar(
                                2019, 3, 20).getTimeInMillis(),
                        new GregorianCalendar(
                                2019, 3, 22).getTimeInMillis(),
                        Color.GREEN),
                EntryFactory.makeEntry(
                        "1",
                        "Hello Motto",
                        new GregorianCalendar(2019, 4, 19),
                        new GregorianCalendar(2019, 4, 19),
                        Color.BLUE),
                EntryFactory.makeEntry(
                        "8",
                        "National",
                        new GregorianCalendar(2019, 4, 19),
                        new GregorianCalendar(2019, 4, 19),
                        Color.BLUE),
                EntryFactory.makeEntry(
                        "2",
                        "aPlease respond",
                        new GregorianCalendar(2019, 4, 19),
                        new GregorianCalendar(2019, 4, 21),
                        Color.RED),
                EntryFactory.makeEntry(
                        "3",
                        "Hey",
                        new GregorianCalendar(
                                2019, 4, 20).getTimeInMillis(),
                        new GregorianCalendar(
                                2019, 4, 20).getTimeInMillis(),
                        Color.GREEN),
                EntryFactory.makeEntry(
                        "adf",
                        "June",
                        new GregorianCalendar(
                                2019, 5, 10).getTimeInMillis(),
                        new GregorianCalendar(
                                2019, 5, 10).getTimeInMillis(),
                        Color.GREEN)
        ));

        tiledMonth.setTiledMonthEventListener(new OnTiledMonthEventListener() {
            @Override
            public void onCellClick(
                    long date,
                    List<String> tileIDs,
                    boolean selectedDateChanged,
                    boolean monthChanged) {
                int i = 0;
            }

            @Override
            public void onTileClick(long date, String id) {
                int i = 0;
            }

            @Override
            public void onSwipe(boolean monthChanged, long date) {
                int i = 0;
            }

            @Override
            public void onMonthChanged(long date) {
                int i = 0;
            }
        });
    }
}
