package ru.funnyhourse.emojilibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ru.funnyhourse.emojilibrary.R;

/**
 * Created by edgar on 23/02/2016.
 */
public class EmojiKeyboardView extends LinearLayout {
    public EmojiKeyboardView(Context context) {
        super(context);
        this.init(context, null);
    }

    public EmojiKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public EmojiKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rsc_emoji_keyboard, this, true);
        this.findViewById(R.id.emoji_keyboard).setVisibility(RelativeLayout.VISIBLE);
        LinearLayout curtain = (LinearLayout) this.findViewById(R.id.curtain);
        curtain.setVisibility(LinearLayout.INVISIBLE);
    }
}
