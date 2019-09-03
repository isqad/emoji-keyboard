package ru.funnyhourse.emojilibrary.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiTabAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.view.EmojiEditTextPanelEventListener;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiNature;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiObjects;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiPeople;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiPlaces;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiRecents;
import ru.funnyhourse.emojilibrary.view.FragmentEmojiSymbols;
import ru.funnyhourse.emojilibrary.view.IEmojiEditTextPanel;

public class EmojiEditTextPanelPresenter implements IEmojiEditTextPanelPresenter, OnEmojiClickListener, IRecentEmojiSaver {
    private EmojiEditTextPanelEventListener mListener;

    private static final String PREFERENCES_FILE = "EmojiProperties";
    private static final String RECENT_EMOJIS = "RECENTS";

    private IEmojiEditTextPanel view;
    private FragmentManager fragmentManager;

    private Boolean isEmojiKeyboardVisible = Boolean.FALSE;
    private Context context;

    private volatile ArrayList<Emoji> recentEmojis = new ArrayList<>();

    public EmojiEditTextPanelPresenter(Context context,
                                       IEmojiEditTextPanel view,
                                       FragmentManager fragmentManager) {
        this.view = view;
        this.view.setPresenter(this);

        this.fragmentManager = fragmentManager;
        this.context = context;

        recentEmojis = getSavedRecentEmojis();

        this.view.initBottomPanel();
        this.view.configureInput();

        initEmojiTabs();

        this.view.setBackspaceBehaviour();
    }

    private void initEmojiTabs() {
        final EmojiTabAdapter emojiTabAdapter = new EmojiTabAdapter(this.fragmentManager);
        emojiTabAdapter.addFragment(new FragmentEmojiRecents(), "RECENTS");
        emojiTabAdapter.addFragment(new FragmentEmojiPeople(), "PEOPLE");
        emojiTabAdapter.addFragment(new FragmentEmojiNature(), "NATURE");
        emojiTabAdapter.addFragment(new FragmentEmojiObjects(), "OBJECTS");
        emojiTabAdapter.addFragment(new FragmentEmojiPlaces(), "PLACES");
        emojiTabAdapter.addFragment(new FragmentEmojiSymbols(), "SYMBOLS");

        emojiTabAdapter.setRecentEmojiSaver(this);

        emojiTabAdapter.setOnEmojiClickListener(this);
        this.view.setViewPagerAdapter(emojiTabAdapter);
    }

    /**
    private void setOnBackPressed() {
        ((IEmojiActivity)this.mActivity).setOnBackPressed(new IOnBackPressedListener() {
            @Override
            public Boolean onBackPressed() {
                if (isEmojiKeyboardVisible) {
                    isEmojiKeyboardVisible = Boolean.FALSE;
                    hideEmojiKeyboard(0);
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }
     **/

    //GETTER AND SETTERS
    //public void setListener(EmojiEditTextPanelEventListener mListener) {
    //    this.mListener = mListener;
    //}

    //public String getText() {
    //    return this.mInput.getText().toString();
    //}

    //public void setText(String text) {
    //    this.mInput.setText(text);
    //}


    @Override
    public void setEventListener(EmojiEditTextPanelEventListener listener) {
        mListener = listener;
    }

    @Override
    public void onBottomPanelNavigationClick() {
        if (isEmojiKeyboardVisible) {
            view.closeCurtain();
            view.toggleSoftKeyboard();
        } else {
            view.closeCurtain();
            view.showKeyboardIcon();

            showEmojiKeyboard(0);
        }
    }

    @Override
    public boolean onBottomPanelMenuItemClick(MenuItem item) {
        if (mListener != null) {
            if (item.getItemId() == R.id.action_attach) {
                mListener.onAttachClicked();
            } else if (item.getItemId() == R.id.action_mic) {
                if (view.getInputText().toString().equals("")) {
                    mListener.onMicClicked();
                } else {
                    mListener.onSendClicked();
                }
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void onSoftKeyboardDisplay() {
        if (!isEmojiKeyboardVisible) {
            view.openCurtain();
            showEmojiKeyboard(200);
        }
    }

    @Override
    public void onSoftKeyboardHidden() {
        if (isEmojiKeyboardVisible) {
            view.closeCurtain();
            hideEmojiKeyboard(200);
        }
    }

    @Override
    public void onEmojiClicked(Emoji emoji) {
        view.appendEmojiToText(emoji);
    }

    @Override
    public void addRecentEmoji(Emoji recentEmoji) {
        int emojiPosition = recentEmojis.indexOf(recentEmoji);
        if(emojiPosition != -1){
            recentEmojis.remove(emojiPosition);
        }
        recentEmojis.add(0,recentEmoji);

        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        final RecentEmojisSaver asyncSaver = new RecentEmojisSaver(sp, recentEmojis);
        asyncSaver.execute();
    }

    private static class RecentEmojisSaver extends AsyncTask<Void, Void, Boolean> {
        private final ArrayList<Emoji> recentEmojis;
        private SharedPreferences sp;

        public RecentEmojisSaver(SharedPreferences sp, ArrayList<Emoji> emojis) {
            recentEmojis = emojis;
            this.sp = sp;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(RECENT_EMOJIS, gson.toJson(recentEmojis));
            return editor.commit();
        }
    }

    @Override
    public ArrayList<Emoji> getRecentEmojis() {
        return recentEmojis;
    }

    private ArrayList<Emoji> getSavedRecentEmojis() {
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:MM:ss").create();
        Type type = new TypeToken<ArrayList<Emoji>>() {
        }.getType();
        SharedPreferences mReader = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        ArrayList<Emoji> list = gson.fromJson(mReader.getString(RECENT_EMOJIS, ""), type);
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    private void showEmojiKeyboard(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isEmojiKeyboardVisible = Boolean.TRUE;
        view.showEmojiKeyboard();
    }

    private void hideEmojiKeyboard(int delay) {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        view.showEmojiIcon();
        isEmojiKeyboardVisible = Boolean.FALSE;
        view.hideEmojiKeyboard();
    }
}
