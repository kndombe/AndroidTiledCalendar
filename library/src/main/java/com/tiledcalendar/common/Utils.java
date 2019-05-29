package com.tiledcalendar.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class Utils {
    public static int dpToPx(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                context.getResources().getDisplayMetrics());
    }

    private static int[] getColorARGB(int color) {
        int alpha = (color>>24)&0xFF;
        int red = (color>>16)&0xFF;
        int green = (color>>8)&0xFF;
        int blue = (color)&0xFF;
        return new int[]{alpha, red, green, blue};
    }

    public static int changeAlphaByPercentage(int color, float alphaPercentage) {
        return Color.argb(
                (int)((float)getColorARGB(color)[0] * alphaPercentage),
                getColorARGB(color)[1],
                getColorARGB(color)[2],
                getColorARGB(color)[3]);
    }
}
