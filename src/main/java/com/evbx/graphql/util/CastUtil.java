package com.evbx.graphql.util;

/**
 * Util to convert values.
 */
public final class CastUtil {

    private CastUtil() {
    }

    /**
     * Attempts to convert a value from object to String.
     *
     * @param value value from data fetcher source or arguments
     * @return Long the value converted to a Long
     */
    public static String toString(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return String.valueOf(value);
        }

        if (value instanceof String) {
            return value.toString();
        }
        return null;
    }

    /**
     * Attempts to convert a value from string or number to a Long.
     *
     * @param value value from data fetcher source or arguments
     * @return Long the value converted to a Long
     */
    public static Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        if (value instanceof String) {
            try {
                return Long.valueOf((String) value);
            } catch (NumberFormatException nfEx) {
                return null;
            }
        }
        return null;
    }
}