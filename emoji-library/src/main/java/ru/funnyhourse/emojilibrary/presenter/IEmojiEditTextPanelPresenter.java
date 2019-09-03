package ru.funnyhourse.emojilibrary.presenter;

import android.view.MenuItem;
import ru.funnyhourse.emojilibrary.view.EmojiEditTextPanelEventListener;

public interface IEmojiEditTextPanelPresenter {
    void onBottomPanelNavigationClick();

    boolean onBottomPanelMenuItemClick(MenuItem item);

    void onSoftKeyboardDisplay();
    void onSoftKeyboardHidden();

    void setEventListener(EmojiEditTextPanelEventListener listener);
}
