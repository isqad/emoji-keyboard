package ru.funnyhourse.emojilibrary.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.view.FragmentEmoji;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiPeople;

public class EmojiTabAdapter extends FragmentPagerAdapter {
    private final List<FragmentEmoji> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private FragmentEmojiPeople FRAGMENT_EMOJI_PEOPLE = new FragmentEmojiPeople();
    /**
    private static FragmentEmojiRecents FRAGMENT_EMOJI_RECENTS = new FragmentEmojiRecents();
    private FragmentEmojiPeople FRAGMENT_EMOJI_PEOPLE = new FragmentEmojiPeople();
    private static FragmentEmojiNature FRAGMENT_EMOJI_NATURE = new FragmentEmojiNature();
    private static FragmentEmojiObjects FRAGMENT_EMOJI_OBJECTS = new FragmentEmojiObjects();
    private static FragmentEmojiPlaces FRAGMENT_EMOJI_PLACES = new FragmentEmojiPlaces();
    private static FragmentEmojiSymbols FRAGMENT_EMOJI_SYMBOLS = new FragmentEmojiSymbols();
    **/

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
        if (position == 1) {
            return "PEOPLE";
        } else {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return FRAGMENT_EMOJI_PEOPLE;
        } else {
            return mFragmentList.get(position);
        }
    }

    public void setOnEmojiClickListener(OnEmojiClickListener listener) {
        Log.d("TAB_ADAPTER", "SET CLICK LISTENER" + listener.toString());

        //for (FragmentEmoji f : mFragmentList) {
        //    f.setEmojiconClickListener(listener);
       // }

        FRAGMENT_EMOJI_PEOPLE.setEmojiconClickListener(listener);
    }
}
