package cn.yayi365.patient.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by chenliu on 2016/4/1.
 * 描述：软键盘工具
 */
public final class KeyBoardUtil {

    /**
     * 关闭键盘
     */
    public static void KeyBoardCancel(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {

            InputMethodManager inputManger = (InputMethodManager) activity
                    .getSystemService(activity.INPUT_METHOD_SERVICE);
            inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 开启键盘  可防止打不开键盘情况  但似乎不是所有手机都适用
     */
    public static void KeyBoardOpenAgain(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 开启键盘
     */
    public static void KeyBoardOpen(Activity activity, View view) {

        InputMethodManager inputmanger = (InputMethodManager) activity
                .getSystemService(activity.INPUT_METHOD_SERVICE);
        inputmanger.showSoftInput(view, 0);
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void KeyBoardHiddent(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager manager = ((InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE));
        if (manager != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }

    }

    public static boolean isOpen(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        return isOpen;
    }
}
