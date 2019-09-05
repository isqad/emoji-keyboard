package ru.funnyhourse.emojilibrary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiAdapter;
import ru.funnyhourse.emojilibrary.model.Nature;
import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;


public final class FragmentEmojiNature extends FragmentEmoji {
    private OnEmojiClickListener mOnEmojiconClickedListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_emoji_nature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GridView gridView = (GridView)view.findViewById(R.id.Emoji_GridView);

        gridView.setAdapter(new EmojiAdapter(view.getContext(), Nature.DATA, false));
        gridView.setOnItemClickListener(this);
    }
}
