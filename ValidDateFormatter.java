
package com.miui.tsmclient.util;

import android.text.Editable;
import android.text.TextUtils;

import java.util.Calendar;

public class ValidDateFormatter extends Formatter {

    private static final char SEPARATOR = '/';

    public boolean isSeparator(char c) {
        return c == SEPARATOR;
    }

    @Override
    public boolean isValidCharacter(char c) {
        return Character.isDigit(c);
    }

    private boolean isExpired(String validDate) {
        if (TextUtils.isEmpty(validDate)) {
            return true;
        }
        int len = validDate.length();
        if (len < 4) {
            return true;
        }

        String validMonth = validDate.substring(0,2);
        String validYear = validDate.substring(len - 2);

        int inputMonth = Integer.parseInt(validMonth);
        int inputYear = Integer.parseInt(validYear);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int curYear = calendar.get(Calendar.YEAR) % 100; // 保留年份后两位
        int curMonth = calendar.get(Calendar.MONTH);

        if (curYear > inputYear || curYear == inputYear && inputMonth < curMonth) {
            return true;
        }
        return false;
    }

    @Override
    public void clean(Editable text) {
        super.clean(text);
        int i = 1;
        while (i < text.length()) {
            if ((text.charAt(i) == '0') && (text.charAt(i-1) == '0')) {
                text.delete(i, i+1);
            } else {
                i++;
            }
        }
    }

    @Override
    public void format(Editable text) {
        String cleanText = clean(text.toString());
        StringBuilder formatText = new StringBuilder(cleanText);
        if (formatText.length() > 4) {
            formatText.delete(4, formatText.length());
        }
        int i = 0;
        while (i < formatText.length()) {
            char ch = formatText.charAt(i);
            boolean needInsertZero = (i == 0 && ch > '1' && ch <= '9') || (i == 1 && ch > '2' && formatText.charAt(0) > '0');
            if (needInsertZero) {
                // 第一位输入大于1： "2" 首位插入0, 第三位插入‘/’ 变为"02/"
                formatText.insert(0, "0");
                break;
            }
            i++;
        }

        if (formatText.length() > 4) {
            formatText.delete(4, formatText.length());
        }

        if (text.length() != 3 && formatText.length() == 2 || (formatText.length() > 2 && formatText.charAt(2) != '/')) {
            formatText.insert(2, "/");
        }

        if (!TextUtils.equals(text, formatText)) {
            text.replace(0, text.length(), formatText);
        }
    }

    @Override
    public void cover(Editable text) {
    }
}
