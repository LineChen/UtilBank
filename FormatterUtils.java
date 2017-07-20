package com.miui.tsmclient.util;

import android.widget.EditText;

public class FormatterUtils {
    public enum FormatterType {
        TYPE_NORMAL(new CommonNumberFormatter()),
        TYPE_VALID_DATE(new ValidDateFormatter()),
        TYPE_PHONE(new PhoneFormatter()),
        TYPE_ID_CARD(new IdCardFormatter());

        private Formatter mFormatter;

        FormatterType (Formatter formatter) {
            mFormatter = formatter;
        }

        public Formatter getFormatter() {
            return mFormatter;
        }
    }

    public static void setFormatter(EditText editText, FormatterType type) {
        type.getFormatter().bindFormattingTextWatcher(editText);
    }

    /**
     * 清除字符串的格式化信息
     * 默认使用TYPE_NORMAL进行clean
     */
    public static String clean(String text) {
        return clean(text, FormatterType.TYPE_NORMAL);
    }

    public static String clean(String text, FormatterType type) {
        return type.getFormatter().clean(text);
    }

    public static String format(String text, FormatterType type) {
        return type.getFormatter().format(text);
    }

    public static String cover(String text, FormatterType type) {
        return type.getFormatter().cover(text);
    }
}
