package ru.funnyhourse.emojilibrary.view;

import android.content.Context;
import android.content.res.Resources;
import android.widget.GridView;

import androidx.annotation.NonNull;
import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiAdapter;
import ru.funnyhourse.emojilibrary.model.EmojiCategory;

public class EmojiGridView extends GridView {
    private EmojiAdapter emojiArrayAdapter;

    public EmojiGridView(final Context context) {
        super(context);

        final Resources resources = getResources();
        final int width = resources.getDimensionPixelSize(R.dimen.emoji_grid_view_column_width);

        setColumnWidth(width);
        setHorizontalSpacing(0);
        setVerticalSpacing(0);
        setPadding(0, 0, 0, 0);
        setNumColumns(AUTO_FIT);
        setClipToPadding(false);
        setVerticalScrollBarEnabled(false);
    }

    public EmojiGridView init(@NonNull final EmojiCategory category,
                              @NonNull final OnEmojiClickListener listener) {
        emojiArrayAdapter = new EmojiAdapter(getContext(), category.getEmojis(), listener);

        setAdapter(emojiArrayAdapter);

        return this;
    }
}
