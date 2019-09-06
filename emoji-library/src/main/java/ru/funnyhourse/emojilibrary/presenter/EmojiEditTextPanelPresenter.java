package ru.funnyhourse.emojilibrary.presenter;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiTabAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.view.EmojiEditTextPanelEventListener;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiNature;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiObjects;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiPeople;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiPlaces;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiRecents;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiSymbols;
import ru.funnyhourse.emojilibrary.view.IEmojiEditTextPanel;

public class EmojiEditTextPanelPresenter implements IEmojiEditTextPanelPresenter, OnEmojiClickListener {
    private EmojiEditTextPanelEventListener mListener;

    private IEmojiEditTextPanel view;

    private boolean isEmojiKeyboardVisible = false;

    private EmojiEditTextPanelPresenter() {}

    public static EmojiEditTextPanelPresenter newInstance(IEmojiEditTextPanel view,
                                                          FragmentManager fm) {
        EmojiEditTextPanelPresenter presenter = new EmojiEditTextPanelPresenter();

        presenter.setView(view);
        presenter.setTabAdapter(new EmojiTabAdapter(fm));

        return presenter;
    }

    /**
    private void setOnBackPressed() {
        ((IEmojiActivity)this.mActivity).setOnBackPressed(new IOnBackPressedListener() {
            @Override
            public Boolean onBackPressed() {
                if (isEmojiKeyboardVisible) {
                    isEmojiKeyboardVisible = Boolean.FALSE;
                    hideEmojiKeyboard(0);
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }
     **/

    //GETTER AND SETTERS
    //public void setListener(EmojiEditTextPanelEventListener mListener) {
    //    this.mListener = mListener;
    //}

    //public String getText() {
    //    return this.mInput.getText().toString();
    //}

    //public void setText(String text) {
    //    this.mInput.setText(text);
    //}

    /**
     * Sets event listener for click to send text, attach and mic icon
     * @param listener EmojiEditTextPanelEventListener
     */
    @Override
    public void setEventListener(EmojiEditTextPanelEventListener listener) {
        mListener = listener;
    }

    /**
     * Event listener for click to keyboard or smile icon
     */
    @Override
    public void onBottomPanelNavigationClick() {
        if (isEmojiKeyboardVisible) {
            isEmojiKeyboardVisible = false;

            boolean isShow = view.showSoftKeyboard(new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    view.hideEmojiKeyboard();
                }
            });

            if (!isShow) {
                view.hideEmojiKeyboard();
            }

            view.showEmojiIcon();
        } else {
            isEmojiKeyboardVisible = true;

            boolean isHide = view.hideSoftKeyboard(new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    view.showEmojiKeyboard();
                }
            });

            // Already hidden
            if (!isHide) {
                view.showEmojiKeyboard();
            }

            view.showKeyboardIcon();
        }
    }

    /**
     * Event listener for click to attach or mic icon
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onBottomPanelMenuItemClick(MenuItem item) {
        if (mListener != null) {
            if (item.getItemId() == R.id.action_attach) {
                mListener.onAttachClicked();
            } else if (item.getItemId() == R.id.action_mic) {
                if (view.getInputText().toString().equals("")) {
                    mListener.onMicClicked();
                } else {
                    mListener.onSendClicked();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * When focus on input show keyboard
     */
    @Override
    public void onSoftKeyboardDisplay() {
        boolean isShow = view.showSoftKeyboard(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                view.hideEmojiKeyboard();
            }
        });

        if (!isShow) {
            view.hideEmojiKeyboard();
        }

        view.showEmojiIcon();

        if (isEmojiKeyboardVisible) {
            isEmojiKeyboardVisible = false;
        }
    }

    @Override
    public void onSoftKeyboardHidden() {
    }

    @Override
    public void onEmojiClicked(Emoji emoji) {
        view.appendEmojiToText(emoji);
    }

    @Override
    public String getText() {
        Editable text = view.getInputText();
        if (text == null) {
            return "";
        } else {
            return text.toString();
        }
    }

    @Override
    public void setText(String text) {
        view.setInputText(text);
    }

    @Override
    public void clearText() {
        view.setInputText("");
    }

    private void showEmojiKeyboard(int delay) {

        Log.d("P", "show emoji");

        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        view.showEmojiKeyboard();
    }

    private void hideEmojiKeyboard(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        view.hideEmojiKeyboard();
    }

    private void setView(IEmojiEditTextPanel view) {
        this.view = view;
        this.view.setPresenter(this);
        this.view.initBottomPanel();
        this.view.configureInput();
        this.view.setBackspaceBehaviour();
    }

    private void setTabAdapter(@NonNull EmojiTabAdapter tabAdapter) {
        tabAdapter.addFragment(new FragmentEmojiRecents(), "RECENTS");
        tabAdapter.addFragment(new FragmentEmojiPeople(), "PEOPLE");
        tabAdapter.addFragment(new FragmentEmojiNature(), "NATURE");
        tabAdapter.addFragment(new FragmentEmojiObjects(), "OBJECTS");
        tabAdapter.addFragment(new FragmentEmojiPlaces(), "PLACES");
        tabAdapter.addFragment(new FragmentEmojiSymbols(), "SYMBOLS");

        this.view.setViewPagerAdapter(tabAdapter);

        tabAdapter.setOnEmojiClickListener(this);
    }
}
