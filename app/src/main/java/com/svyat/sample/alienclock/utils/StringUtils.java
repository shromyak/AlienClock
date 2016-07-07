package com.svyat.sample.alienclock.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MAC on 09.07.16.
 */
public class StringUtils {

    public static List<String> splitCsv(String string) {

        if (TextUtils.isEmpty(string)) return new ArrayList<>(0);
        return Arrays.asList(string.trim().split("\\s*,\\s*"));
    }

    public static boolean contains (Iterable<String> stack, String needle) {

        for (String str: stack) {

            if (TextUtils.equals(str, needle)) return true;

        }

        return false;
    }

    public static <T extends Enum<T>> String enumNamesToCsv(Class<T> clazz) {

        StringBuilder builder = new StringBuilder();

        for (T element: clazz.getEnumConstants()) {
            builder.append(element.name());
            builder.append(",");
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length()-1);
        }

        return builder.toString();
    }
}
