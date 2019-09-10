package ru.funnyhourse.emojilibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.model.Emoji;

public class EmojiEditTextPanel extends LinearLayout implements IEmojiEditTextPanel, OnEmojiClickListener, EmojiEditText.OnSoftKeyboardListener {
    private Toolbar toolbar;

    private EmojiEditText mInput;

    private boolean mToogleIcon = true;

    private EmojiKeyboardView mEmojiKeyboardLayout;

    private boolean isEmojiKeyboardVisible = false;
    private boolean isSoftKeyboardVisible = false;

    private OnEmojiNavigationClickListener navListener;

    public EmojiEditTextPanel(Context context) {
        super(context);
        init(context);
    }

    public EmojiEditTextPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiEditTextPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiEditTextPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(@NonNull Context context) {
        View.inflate(context, R.layout.edit_panel, this);

        mEmojiKeyboardLayout = (EmojiKeyboardView)findViewById(R.id.emoji_keyboard_view);
        mEmojiKeyboardLayout.setClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.panel);
        toolbar.setNavigationIcon(R.drawable.input_emoji);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.inflateMenu(R.menu.toolbar);

        mInput = (EmojiEditText)findViewById(R.id.input);
        mInput.addOnSoftKeyboardListener(this);

        configureInput();
        initBottomPanel();
    }

    public void setOnEmojiNavigationClickListener(@NonNull OnEmojiNavigationClickListener listener) {
        navListener = listener;
    }

    @Override
    public void onEmojiClicked(Emoji emojicon) {
        appendEmojiToText(emojicon);
    }

    @Override
    public void initBottomPanel() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmojiKeyboardVisible) {
                    switchEmojiKeyboardToSoftKeyboard();
                } else {
                    switchSoftKeyboardToEmojiKeyboard();
                }
                mInput.requestFocus();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_attach) {
                    navListener.onAttachClicked();
                } else if (item.getItemId() == R.id.action_mic) {
                    if (getInputText().toString().equals("")) {
                        navListener.onMicClicked();
                    } else {
                        navListener.onSendClicked();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean isSoftKeyboardVisible() {
        return mInput.isSoftKeyboardVisible();
    }

    public boolean isKeyboardsVisible() {
        return isEmojiKeyboardVisible || isSoftKeyboardVisible;
    }

    @Override
    public boolean hideSoftKeyboard(ResultReceiver resultReceiver) {
        isSoftKeyboardVisible = false;
        return mInput.hideSoftKeyboard(resultReceiver);
    }

    @Override
    public boolean showSoftKeyboard(ResultReceiver resultReceiver) {
        isSoftKeyboardVisible = true;
        return mInput.showSoftKeyboard(resultReceiver);
    }

    @Override
    public void showEmojiIcon() {
        toolbar.setNavigationIcon(R.drawable.input_emoji);
    }

    @Override
    public void showKeyboardIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_grey600_24dp);
    }

    @Override
    public Editable getInputText() {
        return mInput.getText();
    }

    @Override
    public void configureInput() {
        final MenuItem micButton = toolbar.getMenu().findItem(R.id.action_mic);
        final View iconAttach = toolbar.findViewById(R.id.action_attach);
        final View iconMic = toolbar.findViewById(R.id.action_mic);

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getInputText().toString().equals("") && mToogleIcon) {
                    mToogleIcon = false;

                    iconAttach.animate().scaleX(0).scaleY(0).setDuration(150).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            iconAttach.setVisibility(View.GONE);
                        }
                    }).start();

                    iconMic.animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(
                            new Runnable() {
                                @Override
                                public void run() {
                                    micButton.setIcon(R.drawable.ic_send_telegram);
                                    iconMic.animate().scaleX(1).scaleY(1).setDuration(75).start();
                                }
                            }).start();

                } else if (getInputText().toString().equals("")) {
                    mToogleIcon = true;

                    iconAttach.animate().scaleX(1).scaleY(1).setDuration(150).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            iconAttach.setVisibility(View.VISIBLE);
                        }
                    }).start();

                    iconMic.animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(
                            new Runnable() {
                                @Override
                                public void run() {
                                    micButton.setIcon(R.drawable.ic_microphone_grey600_24dp);
                                    iconMic.animate().scaleX(1).scaleY(1).setDuration(75).start();
                                }
                            }).start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void showEmojiKeyboard() {
        isEmojiKeyboardVisible = true;

        mEmojiKeyboardLayout.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void hideEmojiKeyboard() {
        isEmojiKeyboardVisible = false;
        mEmojiKeyboardLayout.setVisibility(LinearLayout.GONE);
    }

    private void appendEmojiToText(@NonNull Emoji emoji) {
        int start = mInput.getSelectionStart();
        int end = mInput.getSelectionEnd();
        String emojiSymbol = emoji.getEmoji();

        if (start < 0) {
            mInput.append(emojiSymbol);
        } else {
            Editable editable = mInput.getText();

            if (editable != null)
                editable.replace(
                        Math.min(start, end),
                        Math.max(start, end),
                        emojiSymbol, 0, emojiSymbol.length());
        }
    }

    private void switchEmojiKeyboardToSoftKeyboard() {
        if (!isEmojiKeyboardVisible)
            return;

        boolean isShow = showSoftKeyboard(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                hideEmojiKeyboard();
            }
        });

        if (!isShow) {
            hideEmojiKeyboard();
        }

        showEmojiIcon();
    }

    private void switchSoftKeyboardToEmojiKeyboard() {
        if (isEmojiKeyboardVisible)
            return;

        boolean isHide = hideSoftKeyboard(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                showEmojiKeyboard();
            }
        });

        // Already hidden
        if (!isHide) {
            showEmojiKeyboard();
        }

        showKeyboardIcon();
    }

    @Override
    public void setBackspaceBehaviour() {
        //ImageView mBackspace = (ImageView)findViewById(R.id.backspace);
        //mBackspace.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        mInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        //    }
        //});
    }

    @Override
    public void setInputText(String text) {
        mInput.setText(text);
    }

    public void hideKeyboards() {
        if (isEmojiKeyboardVisible) {
            hideEmojiKeyboard();
        }

        if (isSoftKeyboardVisible) {
            hideSoftKeyboard(new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {}
            });
        }

        showEmojiIcon();

        mInput.clearFocus();
    }

    @Override
    public void onSoftKeyboardBackPressed() {
        hideKeyboards();
    }

    /**
     * Callback when edit text focused or clicked
     */
    @Override
    public void onSoftKeyboardFocus() {
        Log.d("INN", isEmojiKeyboardVisible + ":" + isSoftKeyboardVisible);
        if (isKeyboardsVisible()) {
            return;
        }
        showSoftKeyboard(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {}
        });
    }
}
