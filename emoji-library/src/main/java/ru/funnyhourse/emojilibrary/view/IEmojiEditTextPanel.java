package ru.funnyhourse.emojilibrary.view;

import android.text.Editable;

import ru.funnyhourse.emojilibrary.adapter.EmojiTabAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.presenter.IEmojiEditTextPanelPresenter;

public interface IEmojiEditTextPanel {
    void initBottomPanel();

    void hideSoftKeyboard();
    void showSoftKeyboard();
    boolean isSoftKeyboardVisible();

    void showEmojiKeyboard();
    void hideEmojiKeyboard();

    void openCurtain();
    void closeCurtain();

    void toggleSoftKeyboard();

    void showKeyboardIcon();
    void showEmojiIcon();
    Editable getInputText();

    void configureInput();

    void setPresenter(IEmojiEditTextPanelPresenter presenter);

    void appendEmojiToText(Emoji emoji);

    void setViewPagerAdapter(EmojiTabAdapter adapter);

    void setBackspaceBehaviour();
}
