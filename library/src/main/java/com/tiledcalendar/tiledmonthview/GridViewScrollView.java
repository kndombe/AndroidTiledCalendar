package com.tiledcalendar.tiledmonthview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public final class GridViewScrollView extends HorizontalScrollView {
    public GridViewScrollView(Context context) {
        super(context);
    }

    public GridViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        //TODO: add logic for handling accessibility on-demand click?
        return super.performClick();
    }


}
