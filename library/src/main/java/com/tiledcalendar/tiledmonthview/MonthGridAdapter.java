package com.tiledcalendar.tiledmonthview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tiledcalendar.R;
import com.tiledcalendar.common.Check;
import com.tiledcalendar.common.DateTime;
import com.tiledcalendar.common.Utils;
import com.tiledcalendar.entries.Entry;
import com.tiledcalendar.entries.Tile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

final class MonthGridAdapter extends ArrayAdapter<MonthCell> {

    interface OnCellClickListener {
        void onCellClick(
                long selectedDateMillis,
                boolean dateChanged,
                boolean monthChanged,
                int colorBackground,
                int colorForeground);
    }

    private final String TAG = "MonthGridAdapter";
    private LayoutInflater inflater;
    private List<MonthCell> monthCells = new ArrayList<>();
    private Calendar selectedDate;
    private int gridViewHeight;
    private OnCellClickListener onCellClickListener;

    private int colorBackground = TiledMonthView.COLOR_LIGHT_BACKGROUND;
    private int colorForeground = TiledMonthView.COLOR_LIGHT_FOREGROUND;
    private int colorSelectedDateCell = TiledMonthView.COLOR_LIGHT_SELECTED_DATE_CELL;
    private int colorCurrentDateCell = TiledMonthView.COLOR_LIGHT_CURRENT_DATE_CELL;
    private int colorCurrentDateCellText = TiledMonthView.COLOR_LIGHT_CURRENT_DATE_CELL_TEXT;

    MonthGridAdapter(
            @NonNull Context context,
            Calendar selectedDate) {
        super(context, R.layout.month_view_cell);
        this.inflater = LayoutInflater.from(context);
        this.selectedDate = selectedDate;
    }

    void updateCells(List<MonthCell> monthCells, Calendar newSelectedDate, int gridViewHeight) {
        this.monthCells = monthCells;
        this.selectedDate = newSelectedDate;
        this.gridViewHeight = gridViewHeight;
        notifyDataSetChanged();
    }

    void setOnCellClickListener(@NonNull OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.month_view_cell, parent, false);
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int rows = (int) Math.ceil((double) (monthCells.size()) / 7.0);
        layoutParams.height = gridViewHeight / rows;
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(colorBackground);

        AppCompatTextView cellDate = view.findViewById(R.id.month_view_cell_date);
        cellDate.setTextColor(colorForeground);
        MonthCell cell = getItem(position);
        Check.notNull(cell, TAG);
        boolean isToday = DateTime.isInSameDayAs(cell.getDate(), new GregorianCalendar());
        boolean isSelected = DateTime.isInSameDayAs(cell.getDate(), selectedDate);
        boolean isSameMonth = DateTime.isInSameMonthAs(cell.getDate(), selectedDate);
        GradientDrawable dateBackground = new GradientDrawable();
        dateBackground.mutate();
        dateBackground.setCornerRadius(35f);
        if (isToday) {
            dateBackground.setColor(colorCurrentDateCell);
        }
        if (isSelected) {
            view.setBackgroundColor(colorSelectedDateCell);
        }

        if (isToday) {
            cellDate.setTextColor(colorCurrentDateCellText);
        } else if (isSameMonth) {
            cellDate.setTextColor(colorForeground);
        } else {
            cellDate.setTextColor(Utils.changeAlphaByPercentage(colorForeground, .4f));
        }
        cellDate.setBackground(dateBackground);
        Check.notNull(cell, TAG);
        cellDate.setText(String.valueOf(cell.getDate().get(Calendar.DAY_OF_MONTH)));
        List<Tile> tiles = cell.getTiles();
        LinearLayout tileContainer = view.findViewById(R.id.month_view_cell_tiles);
        tileContainer.removeAllViews();
        for (Tile tile : tiles) {
            addTile(tileContainer, tile);
        }

        setCellListener(view, cell.getDate().getTimeInMillis());
        return view;
    }

    long getCurrentDate() {
        return selectedDate.getTimeInMillis();
    }

    void setThemeColors(
            @Nullable Integer background,
            @Nullable Integer foreground,
            @Nullable Integer selectedDateCell,
            @Nullable Integer currentDateCell,
            @Nullable Integer currentDateCellText
    ) {
        colorBackground = background == null ? colorBackground : background;
        colorForeground = foreground == null ? colorForeground : foreground;
        colorSelectedDateCell = selectedDateCell == null ? colorSelectedDateCell : selectedDateCell;
        colorCurrentDateCell = currentDateCell == null ? colorCurrentDateCell : currentDateCell;
        colorCurrentDateCellText = currentDateCellText == null ?
                colorCurrentDateCellText : currentDateCellText;

    }

    private void addTile(LinearLayout parent, Tile tile) {
        Context context = getContext();
        AppCompatTextView textView = new AppCompatTextView(context);
        textView.setHeight(Utils.dpToPx(20, context));
        int padding = Utils.dpToPx(1, context);
        textView.setPadding(padding, padding, 0, padding);
        textView.setTextColor(Color.WHITE);
        textView.setSingleLine(true);
        textView.setTextSize(Utils.spToPx(4, context));
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, Utils.dpToPx(1, context));
        textView.setLayoutParams(layoutParams);
        parent.addView(textView);
        setEntryLine(textView, tile);
    }

    private void setEntryLine(AppCompatTextView entry, Tile tile) {
        entry.setText(tile.getName());
        GradientDrawable drawable = new GradientDrawable();
        drawable.mutate();
        drawable.setColors(tile.getColors());
        drawable.setCornerRadius(5f);
        entry.setBackgroundDrawable(drawable);
    }

    @Override
    public int getCount() {
        return monthCells.size();
    }

    @Override
    public int getPosition(@Nullable MonthCell item) {
        return monthCells.indexOf(item);
    }

    @Nullable
    @Override
    public MonthCell getItem(int position) {
        return monthCells.get(position);
    }

    private void setCellListener(View view, final long newDateMillis) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long previousSelectedDate = selectedDate.getTimeInMillis();
                selectedDate = DateTime.getDate(newDateMillis);
                if (onCellClickListener != null) {
                    onCellClickListener.onCellClick(
                            newDateMillis,
                            !DateTime.isInSameDayAs(newDateMillis, previousSelectedDate),
                            !DateTime.isInSameMonthAs(newDateMillis, previousSelectedDate),
                            colorBackground,
                            colorForeground);
                }
                notifyDataSetChanged();
            }
        });
    }
}
