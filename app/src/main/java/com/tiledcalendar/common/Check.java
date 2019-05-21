package com.tiledcalendar.common;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public final class Check {

    /**
     * This method checks that the given reference is not null. If the reference is null, a
     * <code>NullPointerException</code> is thrown. Otherwise, the reference is returned.
     * @param reference the variable that is being tested for nullity.
     * @param TAG the tag of the class from which this check is requested.
     * @param <T> the type of the reference.
     * @return the non null reference.
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T notNull(@Nullable T reference, @NonNull String TAG) {
        if (reference == null) {
            throw new NullPointerException("Found an unexpected null reference in " + TAG);
        }
        return reference;
    }

    /**
     * This method returns the first given reference if it's not null. Otherwise it returns the
     * provided default value and logs an error signaling the presence of an unexpected null
     * reference.
     * @param reference the variable that is being tested for nullity.
     * @param defaultValue the default value to be used if the initial reference is null.
     * @param TAG the tag of the class from which this check is requested.
     * @param <T> the type of the reference.
     * @return {@code reference} if it's not null, otherwise {@code defaultValue}.
     */
    @NonNull
    public static <T> T notNull(
            @Nullable T reference, @NonNull T defaultValue, @NonNull String TAG) {
        if (reference == null) {
            Log.e(TAG, "Found an unexpected null reference.");
            return defaultValue;
        } else {
            return reference;
        }
    }

    private Check() {
        throw new AssertionError("Cannot instantiate Check");
    }
}
