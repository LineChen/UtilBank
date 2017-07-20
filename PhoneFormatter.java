package com.miui.tsmclient.util;

import android.text.Editable;

public class PhoneFormatter extends Formatter{

    private static int[] PHONE_NUM_SEP = new int[] {3, 7};

    @Override
    public void format(Editable text) {
        clean(text);
        int i = 0;
        int valid = 0;
        int group = 0;
        while (i < text.length()) {
            if(group < PHONE_NUM_SEP.length && valid == PHONE_NUM_SEP[group]) {
                text.insert(i, Character.toString(SEPARATOR));
                group++;
                i++;
            }
            i++;
            valid++;
        }
    }

}
