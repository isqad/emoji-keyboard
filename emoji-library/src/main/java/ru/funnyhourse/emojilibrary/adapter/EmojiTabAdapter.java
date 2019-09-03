package ru.funnyhourse.emojilibrary.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.funnyhourse.emojilibrary.presenter.IRecentEmojiSaver;
import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.view.FragmentEmoji;

public class EmojiTabAdapter extends FragmentPagerAdapter {
    private final List<FragmentEmoji> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

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
        return mFragmentList.get(position);
    }

    public void setOnEmojiClickListener(OnEmojiClickListener listener) {
        for (FragmentEmoji f : mFragmentList) {
            f.addEmojiconClickListener(listener);
        }
    }

    public void setRecentEmojiSaver(IRecentEmojiSaver saver) {
        for (FragmentEmoji f : mFragmentList) {
            f.setSaver(saver);
        }
    }
}
