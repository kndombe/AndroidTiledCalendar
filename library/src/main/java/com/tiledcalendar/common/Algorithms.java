package com.tiledcalendar.common;

import java.util.List;

import androidx.annotation.NonNull;

public final class Algorithms {

    enum SortOrder {
        SMALLEST_TO_BIGGEST,
        BIGGEST_TO_SMALLEST
    }

    public static <T extends  Comparable<T>> List<T> sortIncreasing(@NonNull List<T> list) {
        return quickSortIncreasing(list);
    }

    public static <T extends  Comparable<T>> List<T> sortDecreasing(@NonNull List<T> list) {
        return quickSortDecreasing(list);
    }

    private static <T extends Comparable<T>> List<T> quickSortIncreasing(
            final List<T> list) {
        return quickSort(list, SortOrder.SMALLEST_TO_BIGGEST);
    }

    private static <T extends Comparable<T>> List<T> quickSortDecreasing(
            final List<T> list) {
        return quickSort(list, SortOrder.BIGGEST_TO_SMALLEST);
    }

    private static <T extends Comparable<T>> List<T> quickSort(
            final List<T> list,
            final SortOrder direction) {
        _quickSort(list, 0, list.size() - 1, direction);
        return list;
    }

    private static <T extends Comparable<T>> void _quickSort(
            final List<T> list,
            final int low,
            final int top,
            final SortOrder direction) {
        {
            if (low < top) {
                int pivot = _quickSortPartition(list, low, top, direction);

                _quickSort(list, low, pivot - 1, direction);
                _quickSort(list, pivot + 1, top, direction);
            }
        }
    }

    private static <T extends Comparable<T>> int _quickSortPartition(
            final List<T> list,
            final int low,
            final int top,
            final SortOrder direction) {
        final int factor = direction == SortOrder.SMALLEST_TO_BIGGEST ? 1 : -1;
        T pivot = list.get(top);
        int i = low;
        int j = top - 1;
        while (true) {
            while (j >= low) {
                if (list.get(j).compareTo(pivot) * factor < 0) {
                    break;
                }
                j--;
            }
            while (i < top) {
                if (list.get(i).compareTo(pivot) * factor >= 0) {
                    break;
                }
                i++;
            }

            if (i < j) {
                _swap(list, i, j);
            } else {
                _swap(list, i, top);
                return i;
            }
        }
    }

    private static <T> void _swap(@NonNull final List<T> list, int i, int j) {
        final T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
