package ru.smartel.strike.util;

public class PaginationUtil {

    //no need to instantiate this util class
    private PaginationUtil() {}

    public static long calculateLastPage(long total, long perPage) {
        return (long) Math.ceil((double) total / (double) perPage);
    }
}
