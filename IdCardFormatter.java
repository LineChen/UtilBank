package com.miui.tsmclient.util;

import android.text.Editable;

public class IdCardFormatter extends Formatter {

    private static int[] ID_CARD_SEP = new int[] {6, 10, 14};

    @Override
    public boolean isValidCharacter(char c) {
        return Character.isDigit(c) || 'x' == Character.toLowerCase(c);
    }

    @Override
    public void format(Editable text) {
        clean(text);
        int i = 0;
        int valid = 0;
        int group = 0;
        while (i < text.length()) {
            if(group < ID_CARD_SEP.length && valid == ID_CARD_SEP[group]) {
                text.insert(i, Character.toString(SEPARATOR));
                group++;
                i++;
            }
            i++;
            valid++;
        }
    }

}
