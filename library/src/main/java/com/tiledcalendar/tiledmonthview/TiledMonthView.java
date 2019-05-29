package com.tiledcalendar.tiledmonthview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.tiledcalendar.R;
import com.tiledcalendar.common.DateTime;
import com.tiledcalendar.entries.Entry;
import com.tiledcalendar.entries.Tile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public final class TiledMonthView extends LinearLayout implements TiledMonthCalendar {

    static final int COLOR_LIGHT_BACKGROUND = Color.WHITE;
    static final int COLOR_LIGHT_FOREGROUND = Color.rgb(80, 80, 80);
    static final int COLOR_LIGHT_SELECTED_DATE_CELL = Color.rgb(230, 230, 230);
    static final int COLOR_LIGHT_CURRENT_DATE_CELL = Color.rgb(100, 100,100);
    static final int COLOR_LIGHT_CURRENT_DATE_CELL_TEXT = Color.rgb(250, 250,250);
    private static final int COLOR_LIGHT_WEEKDAY_BACKGROUND =
            Color.rgb(210, 210, 210);
    private static final int COLOR_LIGHT_WEEKDAY_FOREGROUND = COLOR_LIGHT_FOREGROUND;
    private static final int COLOR_LIGHT_BUTTON_FOREGROUND = COLOR_LIGHT_FOREGROUND;

    static final int COLOR_DARK_BACKGROUND = Color.rgb(50, 50, 50);
    static final int COLOR_DARK_FOREGROUND = Color.WHITE;
    static final int COLOR_DARK_SELECTED_DATE_CELL = Color.rgb(100, 100, 100);
    static final int COLOR_DARK_CURRENT_DATE_CELL = Color.rgb(210, 210,210);
    static final int COLOR_DARK_CURRENT_DATE_CELL_TEXT = Color.rgb(50, 50,50);
    private static final int COLOR_DARK_WEEKDAY_BACKGROUND =
            Color.rgb(80, 80, 80);
    private static final int COLOR_DARK_WEEKDAY_FOREGROUND = COLOR_DARK_FOREGROUND;
    private static final int COLOR_DARK_BUTTON_FOREGROUND = COLOR_DARK_FOREGROUND;

    private Context context;
    private RelativeLayout header;
    private MaterialButton previous;
    private MaterialButton next;
    private AppCompatTextView currentMonthLabel;
    private LinearLayout weekHeaders;
    private GridViewScrollView gridScroll;
    private GridView gridView;
    private GridView previousGridView;
    private GridView nextGridView;
    private int gridViewWidth;
    private int gridViewHeight;
    private MonthGridAdapter gridAdapter;
    private MonthGridAdapter previousGridAdapter;
    private MonthGridAdapter nextGridAdapter;
    private Calendar selectedDate;
    private List<MonthCell> monthCells = new ArrayList<>();
    private List<MonthCell> previousMonthCells = new ArrayList<>();
    private List<MonthCell> nextMonthCells = new ArrayList<>();
    private Map<String, Entry> entriesMap = new HashMap<>();
    private OnTiledMonthEventListener onTiledMonthEventListener;

    public TiledMonthView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TiledMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TiledMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    public long getCurrentDate() {
        return DateTime.getDateStart(GregorianCalendar.getInstance().getTimeInMillis());
    }

    @Override
    public long getCurrentSelected() {
        return DateTime.getDateStart(gridAdapter.getCurrentDate());
    }

    @Override
    public long getFirstVisibleDate() {
        if (monthCells.size() == 0) {
            throw new IllegalStateException("Trying to access cells of an empty calendar");
        }

        return DateTime.getDateStart(monthCells.get(0).getDate().getTimeInMillis());
    }

    @Override
    public long getLastVisibleDate() {
        if (monthCells.size() == 0) {
            throw new IllegalStateException("Trying to access cells of an empty calendar");
        }

        return DateTime.getDateStart(
                monthCells.get(monthCells.size() - 1).getDate().getTimeInMillis());
    }

    @Override
    public void addEntry(@NonNull Entry newEntry) {
        entriesMap.put(newEntry.getUniqueID(), newEntry);
        updateAdapters();
    }

    @Override
    public void addEntries(@NonNull List<Entry> newEntries) {
        for (Entry entry : newEntries) {
            entriesMap.put(entry.getUniqueID(), entry);
        }
        updateAdapters();
    }

    @Override
    public void setTiledMonthEventListener(@NonNull OnTiledMonthEventListener listener) {
        this.onTiledMonthEventListener = listener;
    }

    @Override
    public void activateLightMode() {
        setCustomThemeColors(
                COLOR_LIGHT_BACKGROUND,
                COLOR_LIGHT_FOREGROUND,
                COLOR_LIGHT_SELECTED_DATE_CELL,
                COLOR_LIGHT_CURRENT_DATE_CELL,
                COLOR_LIGHT_CURRENT_DATE_CELL_TEXT,
                COLOR_LIGHT_WEEKDAY_BACKGROUND,
                COLOR_LIGHT_WEEKDAY_FOREGROUND,
                COLOR_LIGHT_BUTTON_FOREGROUND
        );
    }

    @Override
    public void activateDarkMode() {
        setCustomThemeColors(
                COLOR_DARK_BACKGROUND,
                COLOR_DARK_FOREGROUND,
                COLOR_DARK_SELECTED_DATE_CELL,
                COLOR_DARK_CURRENT_DATE_CELL,
                COLOR_DARK_CURRENT_DATE_CELL_TEXT,
                COLOR_DARK_WEEKDAY_BACKGROUND,
                COLOR_DARK_WEEKDAY_FOREGROUND,
                COLOR_DARK_BUTTON_FOREGROUND
        );
    }

    @Override
    public void setCustomThemeColors(
            @Nullable Integer background,
            @Nullable Integer foreground,
            @Nullable Integer selectedDateCell,
            @Nullable Integer currentDateCell,
            @Nullable Integer currentDateCellText,
            @Nullable Integer weekdayBackground,
            @Nullable Integer weekdayForeground,
            @Nullable Integer buttonForeground) {
        setThemeColors(
                background,
                foreground,
                selectedDateCell,
                currentDateCell,
                currentDateCellText,
                weekdayBackground,
                weekdayForeground,
                buttonForeground
        );
        updateAdapters();
    }

    private void init() {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.view_month, this);
        header = view.findViewById(R.id.month_view_header);
        previous = view.findViewById(R.id.month_view_previous);
        next = view.findViewById(R.id.month_view_next);
        currentMonthLabel = view.findViewById(R.id.month_view_current_label);
        weekHeaders = view.findViewById(R.id.month_view_week_headers);
        gridScroll = view.findViewById(R.id.month_view_grid_scroll);
        gridView = view.findViewById(R.id.month_view_grid);
        previousGridView = view.findViewById(R.id.month_view_previous_grid);
        nextGridView = view.findViewById(R.id.month_view_next_grid);
        selectedDate = Calendar.getInstance();

        Calendar previousMonthDate =
                DateTime.relativeTime(selectedDate, Calendar.MONTH, -1);
        Calendar nextMonthDate = DateTime.relativeTime(selectedDate, Calendar.MONTH, 1);
        gridAdapter = new MonthGridAdapter(context, selectedDate);
        previousGridAdapter = new MonthGridAdapter(context, previousMonthDate);
        nextGridAdapter = new MonthGridAdapter(context, nextMonthDate);
        initGridView();
        initThemeColors();
        initListeners();
        view.post(new Runnable() {
            @Override
            public void run() {
                gridViewWidth = view.getWidth();
                gridViewHeight = view.getHeight() - header.getHeight() - weekHeaders.getHeight();
                ViewGroup.LayoutParams params =
                        new LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                params.width = view.getWidth();
                params.height = view.getHeight();
                gridView.setLayoutParams(params);
                previousGridView.setLayoutParams(params);
                nextGridView.setLayoutParams(params);
                scrollGridMiddle();
                gridScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollGridMiddle();
                    }
                });
                initGridView();
            }
        });
    }

    private void initGridView() {
        monthCells = computeGridCells(selectedDate);
        updateAdapters();
        gridView.setAdapter(gridAdapter);
        previousGridView.setAdapter(previousGridAdapter);
        nextGridView.setAdapter(nextGridAdapter);
        initOnCellListener();
    }

    private void initThemeColors() {
        setThemeColors(
                COLOR_LIGHT_BACKGROUND,
                COLOR_LIGHT_FOREGROUND,
                COLOR_LIGHT_SELECTED_DATE_CELL,
                COLOR_LIGHT_CURRENT_DATE_CELL,
                COLOR_LIGHT_CURRENT_DATE_CELL_TEXT,
                COLOR_LIGHT_WEEKDAY_BACKGROUND,
                COLOR_LIGHT_WEEKDAY_FOREGROUND,
                COLOR_LIGHT_BUTTON_FOREGROUND
        );
    }

    private void scrollGridLeft() {
        gridScroll.smoothScrollTo(0, 0);
    }

    private void scrollGridMiddle() {
        gridScroll.smoothScrollTo(gridViewWidth, 0);
    }

    private void scrollGridRight() {
        gridScroll.smoothScrollTo(gridViewWidth * 2, 0);
    }

    private void updateSelectedDate(Calendar newSelectedDate) {
        boolean sameMonth = DateTime.isInSameMonthAs(selectedDate, newSelectedDate);
        selectedDate = newSelectedDate;
        if (!sameMonth && onTiledMonthEventListener != null) {
            onTiledMonthEventListener.onMonthChanged(selectedDate.getTimeInMillis());
        }
    }

    private void computeCurrentLabel() {
        SimpleDateFormat format = new SimpleDateFormat("MMM YYYY", Locale.US);
        String label = format.format(new Date(selectedDate.getTimeInMillis()));
        currentMonthLabel.setText(label);
    }

    private List<MonthCell> computeGridCells(Calendar selectedDate) {
        long firstDayOfMonth = DateTime.getFirstDayOfMonth(selectedDate.getTimeInMillis());
        List<MonthCell> cells = new ArrayList<>();
        Calendar date = DateTime.getDate(firstDayOfMonth);

        // fill in the first (top) week of the month view.
        // start from the first day of the current month and go back by 1 day intervals until the
        // the beginning of the week (Sunday).
        for (int i = date.get(Calendar.DAY_OF_WEEK) - 1; i >= 0; i--) {
            MonthCell cell = new MonthCell(DateTime.getDate(date.getTimeInMillis()));
            cells.add(0, cell);

            date.add(Calendar.DAY_OF_MONTH, -1);
        }

        // fill in the rest of the first week of the month view.
        date = DateTime.getDate(firstDayOfMonth);
        date.add(Calendar.DAY_OF_MONTH, 1); // start the day after the first day of month
        for (int i = date.get(Calendar.DAY_OF_WEEK) - 1; i <= 6; i++) {
            MonthCell cell = new MonthCell(DateTime.getDate(date.getTimeInMillis()));
            cells.add(cell);
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        // fill in the remaining weeks.
        while (DateTime.isInSameMonthAs(date, DateTime.getDate(firstDayOfMonth))) {
            for (int i = 0; i < 7; i++) {
                MonthCell cell = new MonthCell(DateTime.getDate(date.getTimeInMillis()));
                cells.add(cell);
                date.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        return cells;
    }

    private void updateAdapters() {
        Calendar previousMonthDate =
                DateTime.relativeTime(selectedDate, Calendar.MONTH, -1);
        Calendar nextMonthDate = DateTime.relativeTime(selectedDate, Calendar.MONTH, 1);
        List<MonthCell> cells = computeGridCells(selectedDate);
        monthCells = cells;
        updateAdapter(gridAdapter, monthCells, selectedDate);
        cells = computeGridCells(previousMonthDate);
        previousMonthCells = cells;
        updateAdapter(previousGridAdapter, previousMonthCells, previousMonthDate);
        cells = computeGridCells(nextMonthDate);
        nextMonthCells = cells;
        updateAdapter(nextGridAdapter, nextMonthCells, nextMonthDate);
    }

    private void updateAdapter(
            MonthGridAdapter gridAdapter,
            List<MonthCell> monthCells,
            Calendar selectedDate) {
        computeCurrentLabel();
        updateCells(gridAdapter, monthCells, selectedDate);
    }

    private void addEntriesToCell(@NonNull MonthCell cell) {
        for (Entry entry : entriesMap.values()) {
            if (DateTime.occursOnDay(entry, cell.getDate().getTimeInMillis())
            ) {
                cell.addEntry(entry);
            }

        }
    }



    private void updateCells(
            MonthGridAdapter gridAdapter,
            List<MonthCell> monthCells,
            Calendar selectedDate) {

        for (MonthCell cell : monthCells) {
            cell.clearEntries();
            addEntriesToCell(cell);
        }
        gridAdapter.updateCells(monthCells, selectedDate, gridViewHeight);
    }

    private MonthCell getMonthCell(Calendar date) {
        for (MonthCell monthCell : monthCells) {
            if (DateTime.isInSameDayAs(date, monthCell.getDate())) {
                return monthCell;
            }
        }

        return null;
    }

    private void setThemeColors(
            @Nullable Integer background,
            @Nullable Integer foreground,
            @Nullable Integer selectedDateCell,
            @Nullable Integer currentDateCell,
            @Nullable Integer currentDateCellText,
            @Nullable Integer weekdayBackground,
            @Nullable Integer weekdayForeground,
            @Nullable Integer buttonForeground
    ) {
        gridAdapter.setThemeColors(
                background, foreground, selectedDateCell, currentDateCell, currentDateCellText);
        previousGridAdapter.setThemeColors(
                background, foreground, selectedDateCell, currentDateCell, currentDateCellText);
        nextGridAdapter.setThemeColors(
                background, foreground, selectedDateCell, currentDateCell, currentDateCellText);

        if (background != null) {
            header.setBackgroundColor(background);
        }

        if (foreground != null) {
            currentMonthLabel.setTextColor(foreground);
        }

        if (weekdayBackground != null) {
            weekHeaders.setBackgroundColor(weekdayBackground);
        }

        if (weekdayForeground != null) {
            for (int i = 0; i < weekHeaders.getChildCount(); i++) {
                AppCompatTextView text = (AppCompatTextView) weekHeaders.getChildAt(i);
                text.setTextColor(weekdayForeground);
            }
        }

        if (buttonForeground != null) {
            previous.setIconTint(ColorStateList.valueOf(buttonForeground));
            next.setIconTint(ColorStateList.valueOf(buttonForeground));
        }
    }

    private void initOnCellListener() {
        gridAdapter.setOnCellClickListener(new MonthGridAdapter.OnCellClickListener() {
            @Override
            public void onCellClick(
                    long selectedDateMillis,
                    boolean dateChanged,
                    boolean monthChanged,
                    int colorBackground,
                    int colorForeground) {
                updateSelectedDate(DateTime.getDate(selectedDateMillis));
                updateAdapters();

                MonthCell monthCell = getMonthCell(selectedDate);
                if (onTiledMonthEventListener != null) {
                    List<String> ids = monthCell == null ? null : monthCell.getIDs();
                    onTiledMonthEventListener.onCellClick(
                            selectedDateMillis,
                            ids,
                            dateChanged,
                            monthChanged
                    );
                }

                if (monthCell != null && monthCell.getTiles().size() > 0) {
                    showCellDialog(
                            DateTime.getDate(selectedDateMillis),
                            monthCell.getTiles(),
                            colorBackground,
                            colorForeground);
                }
            }
        });
    }

    private void showCellDialog(
            final Calendar date,
            List<Tile> tiles,
            int colorBackground,
            int colorForeground) {
        new CellDialog(
                context,
                date,
                tiles,
                colorBackground,
                colorForeground,
                new CellDialog.OnTileClickedListener() {
                    @Override
                    public void onTileClicked(@NonNull Tile tile) {
                        if (onTiledMonthEventListener != null) {
                            onTiledMonthEventListener.onTileClick(
                                    date.getTimeInMillis(), tile.getUniqueID());
                        }
                    }
        });
    }

    private void setCurrentToPrevious() {
        Calendar newDate = DateTime.getDate(selectedDate.getTimeInMillis());
        newDate.add(Calendar.MONTH, -1);
        updateSelectedDate(newDate);
        scrollGridLeft();
    }

    private void setCurrentToNext() {
        Calendar newDate = DateTime.getDate(selectedDate.getTimeInMillis());
        newDate.add(Calendar.MONTH, 1);
        updateSelectedDate(newDate);
        scrollGridRight();
    }

    private void initListeners() {
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentToPrevious();
                updateAdapters();
                scrollGridMiddle();
            }
        });

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentToNext();
                updateAdapters();
                scrollGridMiddle();
            }
        });

        gridScroll.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (gridScroll.getScrollX() > gridViewWidth * 1.5) {
                        setCurrentToNext();
                        updateAdapters();
                        scrollGridMiddle();
                        if (onTiledMonthEventListener != null) {
                            onTiledMonthEventListener.onSwipe(
                                    true, selectedDate.getTimeInMillis());
                        }
                    } else if (gridScroll.getScrollX() < gridViewWidth * .5) {
                        setCurrentToPrevious();
                        updateAdapters();
                        scrollGridMiddle();
                        if (onTiledMonthEventListener != null) {
                            onTiledMonthEventListener.onSwipe(
                                    true, selectedDate.getTimeInMillis()
                            );
                        }
                    } else {
                        scrollGridMiddle();
                        if (onTiledMonthEventListener != null) {
                            onTiledMonthEventListener.onSwipe(
                                    false, selectedDate.getTimeInMillis()
                            );
                        }
                    }
                    gridScroll.performClick();
                    return true;
                }
                return false;
            }
        });
    }
}
