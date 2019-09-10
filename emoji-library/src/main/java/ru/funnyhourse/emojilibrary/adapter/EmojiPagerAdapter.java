package ru.funnyhourse.emojilibrary.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import ru.funnyhourse.emojilibrary.model.EmojiRepository;
import ru.funnyhourse.emojilibrary.view.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.view.EmojiGridView;

/**
 * Adapter for Tabs
 */
public final class EmojiPagerAdapter extends PagerAdapter {
    private OnEmojiClickListener listener;

    public void setListener(OnEmojiClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return EmojiRepository.INSTANCE.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View newView;

        newView = new EmojiGridView(container.getContext()).init(
                EmojiRepository.INSTANCE.getCategories()[position],
                listener
        );

        container.addView(newView);

        return newView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
