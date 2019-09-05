package ru.funnyhourse.emojilibrary.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.model.People;
import ru.funnyhourse.emojilibrary.model.RecentEmojiStorage;
import ru.funnyhourse.emojilibrary.presenter.OnEmojiClickListener;

public final class FragmentEmojiPeople extends Fragment implements AdapterView.OnItemClickListener {
    private OnEmojiClickListener mOnEmojiconClickedListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_emoji_people, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.Emoji_GridView);
        Log.d("FRAGMENT", "onViewCreated");

        gridView.setAdapter(new EmojiAdapter(view.getContext(), People.DATA, false));
        gridView.setOnItemClickListener(this);

        Log.d("FRAGMENT onViewCreated", this.toString());

        if (savedInstanceState != null)
            Log.d("FRAGMENT savedInstanceS", savedInstanceState.toString());
    }

    public void setEmojiconClickListener(OnEmojiClickListener listener) {
        Log.d("FRAGMENT setListener", this.toString());
        this.mOnEmojiconClickedListener = listener;
    }

    @Override
    public void onItemClick(@NonNull AdapterView<?> parent, View view, int position, long id) {
        Log.d("FRAGMENT on click", this.toString());
        //if (mOnEmojiconClickedListener == null) {
        //    return;
        //}

        final Emoji clickedEmoji = (Emoji) parent.getItemAtPosition(position);

        Log.d("FRAGMENT", clickedEmoji.toString());

        //mOnEmojiconClickedListener.onEmojiClicked(clickedEmoji);

        //RecentEmojiStorage.addRecentEmoji(clickedEmoji);

        //if(mRecentListener != null) {
        //    mRecentListener.notifyEmojiAdded();
       // }
    }
}
