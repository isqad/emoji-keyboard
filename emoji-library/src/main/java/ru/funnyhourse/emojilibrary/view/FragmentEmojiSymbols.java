package ru.funnyhourse.emojilibrary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiAdapter;
import ru.funnyhourse.emojilibrary.model.Symbols;

public final class FragmentEmojiSymbols extends FragmentEmoji {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_emoji_symbols, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.Emoji_GridView);
        gridView.setAdapter(new EmojiAdapter(view.getContext(), Symbols.DATA, false));
        gridView.setOnItemClickListener(this);
    }
}
