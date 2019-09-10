package ru.funnyhourse.emojilibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.ResultReceiver;
import android.text.InputType;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.util.EmojiUtil;
import ru.funnyhourse.emojilibrary.util.SoftKeyboardUtil;

public class EmojiEditText extends AppCompatEditText {
    private Context mContext;
    private OnSoftKeyboardListener mOnSoftKeyboardListener;
    private boolean isSoftKeyboardVisible = false;

    private int mEmojiconSize;
    private int mEmojiconAlignment;
    private int mEmojiconTextSize;
    private boolean mUseSystemDefault = Boolean.FALSE;

    // CONSTRUCTORS
    public EmojiEditText(Context context) {
        super(context);
        this.init(context, null);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initFocusListener();
    }

    // INITIALIZATIONS
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        disableShowSoftInputOnFocus();

        initFocusListener();

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Emoji);
        mEmojiconSize = (int) a.getDimension(R.styleable.Emoji_emojiSize, getTextSize());
        mEmojiconAlignment = a.getInt(R.styleable.Emoji_emojiAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
        mUseSystemDefault = a.getBoolean(R.styleable.Emoji_emojiUseSystemDefault, false);
        a.recycle();
        mEmojiconTextSize = (int) getTextSize();
        setText(getText());
    }

    private void disableShowSoftInputOnFocus() {
        if (Build.VERSION.SDK_INT >= 19) {
            setShowSoftInputOnFocus(false);
        } else {
            setRawInputType(InputType.TYPE_NULL);
            setFocusable(false);
        }
    }

    private void initFocusListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSoftKeyboardListener != null)
                    mOnSoftKeyboardListener.onSoftKeyboardFocus();
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOnSoftKeyboardListener != null)
                        mOnSoftKeyboardListener.onSoftKeyboardFocus();
                }
            }
        });
    }

    // CALLBACKS
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mOnSoftKeyboardListener != null)
                mOnSoftKeyboardListener.onSoftKeyboardBackPressed();
            clearFocus();
        }
        return true;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.updateText();
    }

    // EMOJI HANDLER
    private void updateText() {
        EmojiUtil.addEmojis(
                this.getContext(),
                this.getText(),
                this.mEmojiconSize,
                this.mEmojiconAlignment,
                this.mEmojiconTextSize,
                this.mUseSystemDefault
        );
    }

    // SOFT KEYBOARD LISTENER
    public boolean showSoftKeyboard(ResultReceiver resultReceiver) {
        isSoftKeyboardVisible = true;

        return SoftKeyboardUtil.showSoftKeyboard(this.mContext, this, resultReceiver);
    }

    public boolean hideSoftKeyboard(ResultReceiver resultReceiver) {
        isSoftKeyboardVisible = false;

        return SoftKeyboardUtil.dismissSoftKeyboard(this.mContext, this, resultReceiver);
    }

    public interface OnSoftKeyboardListener {
        void onSoftKeyboardFocus();
        void onSoftKeyboardBackPressed();
    }

    // GETTERS AND SETTERS
    public Boolean isSoftKeyboardVisible() {
        return isSoftKeyboardVisible;
    }

    public void addOnSoftKeyboardListener(OnSoftKeyboardListener mOnSoftKeyboardListener) {
        this.mOnSoftKeyboardListener = mOnSoftKeyboardListener;
    }

    public void setEmojiSize(int pixels) {
        this.mEmojiconSize = pixels;
        this.updateText();
    }

    public void setUseSystemDefault(boolean useSystemDefault) {
        this.mUseSystemDefault = useSystemDefault;
    }
}
