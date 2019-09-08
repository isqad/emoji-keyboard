package ru.funnyhourse.emojilibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.ViewPager;
import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiPagerAdapter;
import ru.funnyhourse.emojilibrary.model.EmojiRepository;
import ru.funnyhourse.emojilibrary.util.EmojiUtil;

public final class EmojiKeyboardView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private int themeIconColor;

    private ImageButton[] tabs = new ImageButton[5];

    private EmojiPagerAdapter emojiPagerAdapter;

    public EmojiKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(@NonNull final Context context) {
        View.inflate(context, R.layout.emoji_keyboard, this);

        themeIconColor = EmojiUtil.resolveColor(context, R.attr.emojiIcons, R.color.emoji_icons);

        final ViewPager emojisPager = findViewById(R.id.emoji_viewpager);
        final LinearLayout emojisTab = findViewById(R.id.tab_container);
        int categoriesNums = EmojiRepository.INSTANCE.size();

        for (int i = 0; i < categoriesNums; i++) {
            ImageButton tab = inflateButton(context, EmojiRepository.INSTANCE.at(i).getIcon(), 0, emojisTab);
            tab.setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
            tabs[i] = tab;
        }
        emojisPager.addOnPageChangeListener(this);

        emojiPagerAdapter = new EmojiPagerAdapter();
        emojisPager.setAdapter(emojiPagerAdapter);
    }

    public void setClickListener(@NonNull OnEmojiClickListener clickListener) {
        emojiPagerAdapter.setListener(clickListener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private ImageButton inflateButton(final Context context,
                                      final int icon,
                                      final int categoryName,
                                      final ViewGroup parent) {
        final ImageButton button = (ImageButton) LayoutInflater.from(context).inflate(
                R.layout.emoji_view_category,
                parent, false);

        button.setImageDrawable(AppCompatResources.getDrawable(context, icon));
        button.setColorFilter(themeIconColor, PorterDuff.Mode.SRC_IN);
        //button.setContentDescription(context.getString(categoryName));

        parent.addView(button);

        return button;
    }

    static class EmojiTabsClickListener implements OnClickListener {
        private final ViewPager emojisPager;
        private final int position;

        EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
            this.emojisPager = emojisPager;
            this.position = position;
        }

        @Override public void onClick(final View v) {
            emojisPager.setCurrentItem(position);
        }
    }
}
