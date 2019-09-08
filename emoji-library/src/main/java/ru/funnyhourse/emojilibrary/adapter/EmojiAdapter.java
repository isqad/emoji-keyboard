package ru.funnyhourse.emojilibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.view.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.view.EmojiTextView;

/**
 * Adapter for EmojiGridView
 */
public final class EmojiAdapter extends ArrayAdapter<Emoji> {
    private final static String TAG = "EmojiAdapter";

    private OnEmojiClickListener listener;

    public EmojiAdapter(Context context, Emoji[] data, @NonNull OnEmojiClickListener listener) {
        super(context, R.layout.rsc_emoji_item, data);

        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final Emoji emoji = getItem(position);
        if (view == null) {
            view = View.inflate(getContext(), R.layout.rsc_emoji_item, null);
            view.setTag(new ViewHolder(view, false));
        }

        if (emoji != null) {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.icon.setText(emoji.getEmoji());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEmojiClicked(emoji);
                }
            });
        }

        return view;
    }

    static class ViewHolder {
        EmojiTextView icon;

        public ViewHolder(@NonNull View view, Boolean useSystemDefault) {
            icon = (EmojiTextView)view.findViewById(R.id.emoji_icon);
        }
    }
}
