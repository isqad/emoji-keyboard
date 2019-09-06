package ru.funnyhourse.emojilibrary.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.view.FragmentEmoji;

public class EmojiTabAdapter extends FragmentPagerAdapter {
    private final List<FragmentEmoji> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private OnEmojiClickListener listener;

    public EmojiTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(FragmentEmoji fragmentEmoji, String title) {
        mFragmentList.add(fragmentEmoji);
        mFragmentTitleList.add(title);
    }

    // CALLBACKS
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        FragmentEmoji f = mFragmentList.get(position);
        f.setEmojiconClickListener(listener);

        return f;
    }

    public void setOnEmojiClickListener(OnEmojiClickListener listener) {
        if (this.listener == null)
            this.listener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        FragmentEmoji f = (FragmentEmoji) super.instantiateItem(container, position);
        f.setEmojiconClickListener(listener);
        mFragmentList.set(position, f);
        return f;
    }
}
