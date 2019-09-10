package ru.funnyhourse.emojilibrary.util;

import android.content.Context;
import android.os.ResultReceiver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class SoftKeyboardUtil {
    public static boolean dismissSoftKeyboard(Context context, EditText editText, ResultReceiver resultReceiver) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isHide = false;

        if (imm != null) {
            isHide = imm.hideSoftInputFromWindow(editText.getWindowToken(), 0, resultReceiver);
        }

        return isHide;
    }

    public static boolean showSoftKeyboard(Context context, EditText editText, ResultReceiver resultReceiver) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isShow = false;

        if (imm != null) {
            isShow = imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED, resultReceiver);
        }

        return isShow;
    }
}
