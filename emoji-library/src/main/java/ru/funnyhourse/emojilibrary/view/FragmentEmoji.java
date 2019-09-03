package ru.funnyhourse.emojilibrary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.presenter.IRecentEmojiSaver;
import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;
import ru.funnyhourse.emojilibrary.util.TimestampUtil;

public class FragmentEmoji extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "FragmentEmoji";

    private RecentEmojiListener mRecentListener;
    private OnEmojiClickListener mOnEmojiconClickedListener;

    protected IRecentEmojiSaver saver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_emoji_people, container, false);
    }

    public void setSaver(IRecentEmojiSaver saver) {
        this.saver = saver;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnEmojiconClickedListener == null) {
            return;
        }

        final Emoji clickedEmoji = (Emoji) parent.getItemAtPosition(position);

        mOnEmojiconClickedListener.onEmojiClicked(clickedEmoji);
        clickedEmoji.setTimestamp(TimestampUtil.getCurrentTimestamp());

        saver.addRecentEmoji(clickedEmoji);
        if(mRecentListener != null){
            mRecentListener.notifyEmojiAdded();
        }
    }

    public void addEmojiconClickListener(OnEmojiClickListener listener) {
        this.mOnEmojiconClickedListener = listener;
    }

    public void subscribeRecentListener(RecentEmojiListener listener) {
        this.mRecentListener = listener;
    }
}
