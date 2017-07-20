package com.miui.tsmclient.util;

import android.text.Editable;

/**
 * 每四位添加一空格
 */
public class CommonNumberFormatter extends Formatter {

    @Override
    public void format(Editable text) {
        clean(text);
        if (text.length() <= 4) {
            return;
        }
        int i = 0;
        int valid = 0;
        while (i < text.length()) {
            if (valid % 4 == 0 && valid != 0) {
                text.insert(i, Character.toString(SEPARATOR));
                i++;
            }
            i++;
            valid++;
        }
    }
}
