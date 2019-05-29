package com.tiledcalendar.tiledmonthview;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.tiledcalendar.R;
import com.tiledcalendar.common.DateTime;
import com.tiledcalendar.common.Utils;
import com.tiledcalendar.entries.Tile;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

final class CellDialog extends Dialog {

    interface OnTileClickedListener {
        void onTileClicked(@NonNull Tile tile);
    }

    private Context context;
    private Calendar date;
    private List<Tile> tiles;
    private int colorBackground;
    private int colorForeground;
    private OnTileClickedListener onTileClickedListener;

    private ConstraintLayout container;
    private MaterialButton closeButton;
    private AppCompatTextView dateTextView;
    private LinearLayout tilesLayout;

    private static CellDialog lastOpen = null;

    CellDialog(
            @NonNull Context context,
            Calendar date,
            List<Tile> tiles,
            int colorBackground,
            int colorForeground,
            OnTileClickedListener onTileClickedListener) {
        super(context);
        this.context = context;
        this.date = date;
        this.tiles = tiles;
        this.colorBackground = colorBackground;
        this.colorForeground = colorForeground;
        this.onTileClickedListener = onTileClickedListener;
        init();
    }

    private void init() {

        setContentView(R.layout.dialog_cell);
        container = findViewById(R.id.dialog_cell_container);
        closeButton = findViewById(R.id.dialog_cell_close);
        dateTextView = findViewById(R.id.dialog_cell_date);
        tilesLayout = findViewById(R.id.dialog_cell_tiles);

        container.setBackgroundColor(colorBackground);
        closeButton.setIconTint(ColorStateList.valueOf(colorForeground));
        setDate();
        setTiles();
        setCloseButtonListeners();
        show();
    }

    @Override
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    public void show() {
        if (lastOpen != null) {
            lastOpen.dismiss();
        }
        super.show();
        lastOpen = this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        lastOpen = null;
    }

    private void setDate() {
        dateTextView.setText(DateTime.getFormattedDate(date.getTimeInMillis()));
        dateTextView.setTextColor(colorForeground);
    }

    private void setTiles() {
        for (Tile tile : tiles) {
            addTile(tilesLayout, tile);
        }
    }

    // TODO: close duplicate of the method of same name in MonthGridAdapter. Put in Utils?
    private void addTile(final LinearLayout parent, Tile tile) {
        final AppCompatTextView textView = new AppCompatTextView(context);
        int padding = Utils.dpToPx(4, context);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextColor(Color.WHITE);
        textView.setMinimumWidth(100000000); // TODO: hacky way of having expand through. Clean!
        textView.setTextSize(Utils.spToPx(6, context));
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, Utils.dpToPx(10, context));
        textView.setLayoutParams(layoutParams);
        parent.addView(textView);
        setEntryLine(textView, tile);
        setTileListener(textView, tile);
    }

    // TODO: duplicate of the method of same name in MonthGridAdapter. Put in Utils?
    private void setEntryLine(AppCompatTextView entry, Tile tile) {
        entry.setText(tile.getName());
        GradientDrawable drawable = new GradientDrawable();
        drawable.mutate();
        drawable.setColors(tile.getColors());
        drawable.setCornerRadius(15f);
        entry.setBackgroundDrawable(drawable);
    }

    private void setTileListener(AppCompatTextView textView, final Tile tile) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTileClickedListener.onTileClicked(tile);
            }
        });
    }

    private void setCloseButtonListeners() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
