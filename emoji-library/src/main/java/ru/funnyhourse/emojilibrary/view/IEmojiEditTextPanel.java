package ru.funnyhourse.emojilibrary.view;

import android.os.ResultReceiver;
import android.text.Editable;


public interface IEmojiEditTextPanel {
    void initBottomPanel();

    boolean hideSoftKeyboard(ResultReceiver resultReceiver);
    boolean showSoftKeyboard(ResultReceiver resultReceiver);

    boolean isSoftKeyboardVisible();

    void showEmojiKeyboard();
    void hideEmojiKeyboard();

    void showKeyboardIcon();
    void showEmojiIcon();
    Editable getInputText();

    void configureInput();

    void setBackspaceBehaviour();

    void setInputText(String text);
}
