package ru.funnyhourse.emojilibrary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import java.util.List;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.model.RecentEmojiStorage;

public final class FragmentEmojiRecents extends FragmentEmoji implements RecentEmojiListener {
    private EmojiAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_emoji_recents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GridView mGridView = (GridView) view.findViewById(R.id.Emoji_GridView);
        List<Emoji> mData = RecentEmojiStorage.getRecentEmojis();

        if (mData.size() > 0) {
            mAdapter = new EmojiAdapter(view.getContext(), mData, false);
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(this);
        }
    }

    @Override
    public void notifyEmojiAdded() {
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
}
