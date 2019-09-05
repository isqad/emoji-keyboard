package ru.funnyhourse.emojilibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.adapter.EmojiTabAdapter;
import ru.funnyhourse.emojilibrary.model.Emoji;
import ru.funnyhourse.emojilibrary.presenter.IEmojiEditTextPanelPresenter;

public class EmojiEditTextPanel extends LinearLayout implements IEmojiEditTextPanel {
    private Toolbar mBottomPanel;

    private EmojiEditText mInput;

    private Boolean mToogleIcon = Boolean.TRUE;

    private RelativeLayout mEmojiKeyboardLayout;

    private IEmojiEditTextPanelPresenter presenter;

    private ImageView[] mTabIcons = new ImageView[6];

    public EmojiEditTextPanel(Context context) {
        super(context);
        init(context);
    }

    public EmojiEditTextPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiEditTextPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiEditTextPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    // INITIALIZATIONS
    private void init(@NonNull Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null)
            inflater.inflate(R.layout.edit_panel, this, true);

        mBottomPanel = (Toolbar)findViewById(R.id.panel);
        mInput = (EmojiEditText)mBottomPanel.findViewById(R.id.input);
        mEmojiKeyboardLayout = (RelativeLayout) findViewById(R.id.emoji_keyboard);
    }

    @Override
    public void setPresenter(IEmojiEditTextPanelPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initBottomPanel() {
        mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
        mBottomPanel.setTitleTextColor(0xFFFFFFFF);
        mBottomPanel.inflateMenu(R.menu.telegram_menu);

        mBottomPanel.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBottomPanelNavigationClick();
            }
        });
        mBottomPanel.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return presenter.onBottomPanelMenuItemClick(item);
            }
        });
    }

    @Override
    public boolean isSoftKeyboardVisible() {
        return mInput.isSoftKeyboardVisible();
    }

    @Override
    public boolean hideSoftKeyboard(ResultReceiver resultReceiver) {
        return mInput.hideSoftKeyboard(resultReceiver);
    }

    @Override
    public boolean showSoftKeyboard(ResultReceiver resultReceiver) {
        return mInput.showSoftKeyboard(resultReceiver);
    }

    @Override
    public void showEmojiIcon() {
        mBottomPanel.setNavigationIcon(R.drawable.input_emoji);
    }

    @Override
    public void showKeyboardIcon() {
        mBottomPanel.setNavigationIcon(R.drawable.ic_keyboard_grey600_24dp);
    }

    @Override
    public Editable getInputText() {
        return mInput.getText();
    }

    @Override
    public void configureInput() {
        final MenuItem micButton = mBottomPanel.getMenu().findItem(R.id.action_mic);
        final View iconAttach = mBottomPanel.findViewById(R.id.action_attach);
        final View iconMic = mBottomPanel.findViewById(R.id.action_mic);

        mInput.addOnSoftKeyboardListener(new EmojiEditText.OnSoftKeyboardListener() {
            @Override
            public void onSoftKeyboardDisplay() {
                presenter.onSoftKeyboardDisplay();
            }

            @Override
            public void onSoftKeyboardHidden() {
                presenter.onSoftKeyboardHidden();
            }
        });

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getInputText().toString().equals("") && mToogleIcon) {
                    mToogleIcon = Boolean.FALSE;

                    iconAttach.animate().scaleX(0).scaleY(0).setDuration(150).start();
                    iconAttach.setVisibility(View.GONE);

                    iconMic.animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(
                            new Runnable() {
                                @Override
                                public void run() {
                                    micButton.setIcon(R.drawable.ic_send_telegram);
                                    iconMic.animate().scaleX(1).scaleY(1).setDuration(75).start();
                                }
                            }).start();

                } else if (getInputText().toString().equals("")) {
                    mToogleIcon = Boolean.TRUE;

                    iconAttach.setVisibility(View.VISIBLE);
                    iconAttach.animate().scaleX(1).scaleY(1).setDuration(150).start();

                    iconMic.animate().scaleX(0).scaleY(0).setDuration(75).withEndAction(
                            new Runnable() {
                                @Override
                                public void run() {
                                    micButton.setIcon(R.drawable.ic_microphone_grey600_24dp);
                                    iconMic.animate().scaleX(1).scaleY(1).setDuration(75).start();
                                }
                            }).start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void showEmojiKeyboard() {
        mEmojiKeyboardLayout.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void hideEmojiKeyboard() {
        mEmojiKeyboardLayout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public void appendEmojiToText(Emoji emoji) {
        int start = mInput.getSelectionStart();
        int end = mInput.getSelectionEnd();
        String emojiSymbol = emoji.getEmoji();

        if (start < 0) {
            mInput.append(emojiSymbol);
        } else {
            Editable editable = mInput.getText();

            if (editable != null)
                editable.replace(
                        Math.min(start, end),
                        Math.max(start, end),
                        emojiSymbol, 0, emojiSymbol.length());
        }
    }

    @Override
    public void setViewPagerAdapter(EmojiTabAdapter adapter) {
        final ViewPager viewPager = (ViewPager)findViewById(R.id.emoji_viewpager);
        viewPager.setAdapter(adapter);

        final SmartTabLayout viewPagerTab = (SmartTabLayout)findViewById(R.id.emoji_tabs);
        final LayoutInflater inf = LayoutInflater.from(getContext().getApplicationContext());
/**
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inf.inflate(R.layout.rsc_emoji_tab_icon_view, container, false);
                switch (position) {
                    case 0:
                        mTabIcons[0] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        break;
                    case 1:
                        mTabIcons[1] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_people_light_normal);
                        break;
                    case 2:
                        mTabIcons[2] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        break;
                    case 3:
                        mTabIcons[3] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        break;
                    case 4:
                        mTabIcons[4] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_places_light_normal);
                        break;
                    case 5:
                        mTabIcons[5] = icon;
                        icon.setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 6:
                        icon.setImageResource(R.drawable.sym_keyboard_delete_holo_dark);
                        break;
                }
                return icon;
            }
        });

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        // adapter.updateRecentEmojis();
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_activated);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 1:
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_activated);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 2:
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_activated);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 3:
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_activated);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 4:
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_activated);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_normal);
                        break;
                    case 5:
                        mTabIcons[0].setImageResource(R.drawable.ic_emoji_recent_light_normal);
                        mTabIcons[1].setImageResource(R.drawable.ic_emoji_people_light_normal);
                        mTabIcons[2].setImageResource(R.drawable.ic_emoji_nature_light_normal);
                        mTabIcons[3].setImageResource(R.drawable.ic_emoji_objects_light_normal);
                        mTabIcons[4].setImageResource(R.drawable.ic_emoji_places_light_normal);
                        mTabIcons[5].setImageResource(R.drawable.ic_emoji_symbols_light_activated);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
 **/
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void setBackspaceBehaviour() {
        ImageView mBackspace = (ImageView)findViewById(R.id.backspace);
        mBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
    }
}
