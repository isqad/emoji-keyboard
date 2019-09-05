package ru.funnyhourse.emojilibrary.view;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.model.RecentEmojiStorage;
import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;

public class FragmentEmoji extends Fragment implements AdapterView.OnItemClickListener {
    protected RecentEmojiListener mRecentListener;
    protected OnEmojiClickListener mOnEmojiconClickedListener;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("FRAGMENT", mOnEmojiconClickedListener.toString());

        if (mOnEmojiconClickedListener == null) {
            return;
        }

        final Emoji clickedEmoji = (Emoji) parent.getItemAtPosition(position);

        mOnEmojiconClickedListener.onEmojiClicked(clickedEmoji);

        RecentEmojiStorage.addRecentEmoji(clickedEmoji);

        if(mRecentListener != null) {
            mRecentListener.notifyEmojiAdded();
        }
    }

    public void setEmojiconClickListener(OnEmojiClickListener listener) {
        this.mOnEmojiconClickedListener = listener;
    }

    public void subscribeRecentListener(RecentEmojiListener listener) {
        this.mRecentListener = listener;
    }
}
