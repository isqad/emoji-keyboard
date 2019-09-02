package ru.funnyhourse.emojikeyboard.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.funnyhourse.emojikeyboard.R;
import ru.funnyhourse.emojikeyboard.adapter.MessageAdapter;
import ru.funnyhourse.emojikeyboard.model.Message;
import ru.funnyhourse.emojikeyboard.model.MessageType;
import ru.funnyhourse.emojikeyboard.util.TimestampUtil;
import ru.funnyhourse.emojilibrary.controller.TelegramPanel;
import ru.funnyhourse.emojilibrary.model.layout.EmojiCompatActivity;
import ru.funnyhourse.emojilibrary.model.layout.IEmojiActivity;
import ru.funnyhourse.emojilibrary.model.layout.IOnBackPressedListener;
import ru.funnyhourse.emojilibrary.model.layout.TelegramPanelEventListener;


/**
 * Created by edgar on 17/02/2016.
 */
public class ActivityTelegram extends AppCompatActivity implements TelegramPanelEventListener, IEmojiActivity {

    public static final String TAG = "ActivityTelegram";

    private IOnBackPressedListener mOnBackPressedListener;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private TelegramPanel mBottomPanel;
    private RecyclerView mMessages;
    private MessageAdapter mAdapter;

    // CALLBACKS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_telegram);

        this.initToolbar();
        this.initDrawerMenu();
        this.setTelegramTheme();
        this.initMessageList();

        this.mBottomPanel = new TelegramPanel(this,
                                              (View)findViewById(R.id.mainlayout),
                                              this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toogle:
                Intent intent = new Intent(ActivityTelegram.this, ActivityWhatsApp.class);
                ActivityTelegram.this.startActivity(intent);
                ActivityTelegram.this.finish();
                break;
            case android.R.id.home:
                if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    this.mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    this.mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // INITIALIZATIONS
    private void initDrawerMenu() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.findViewById(R.id.github_thumbnail);

        CircularImageView thumbnail = (CircularImageView) this.findViewById(R.id.github_thumbnail);

        Picasso.with(this)
                .load(R.drawable.github)
                .resize(60, 60)
                .centerCrop()
                .into(thumbnail);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/instachat/emoji-library"));
                startActivity(browserIntent);
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                this.mDrawerLayout,
                ActivityTelegram.this.mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        this.mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initMessageList() {
        this.mMessages = (RecyclerView) this.findViewById(R.id.messages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(Boolean.TRUE);
        this.mMessages.setLayoutManager(linearLayoutManager);
        this.mAdapter = new MessageAdapter(new ArrayList<Message>());
        this.mMessages.setAdapter(mAdapter);
    }

    private void initToolbar() {
        this.mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.mToolbar.setTitleTextColor(0xFFFFFFFF);
        this.mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        this.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.telegram_bkg));
        this.setSupportActionBar(this.mToolbar);
        this.getSupportActionBar().setTitle("Telegram");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setTelegramTheme() {
        ActivityTelegram.this.mToolbar.setTitle("Telegram");
        ActivityTelegram.this.getWindow().setBackgroundDrawable(ActivityTelegram.this.getResources().getDrawable(R.drawable.telegram_bkg));
        this.mToolbar.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryTelegram));
        if (Build.VERSION.SDK_INT >= 21) {
            this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryTelegram));
        }
    }

    // TELEGRAM PANEL INTERFACE
    @Override
    public void onAttachClicked() {
        Toast.makeText(ActivityTelegram.this, "Attach was clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMicClicked() {
        Log.i(TAG, "Mic was clicked");
        Toast.makeText(ActivityTelegram.this, "Mic was clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendClicked() {
        Log.i(TAG, "message: " + this.mBottomPanel.getText());
        Message message = new Message();
        message.setType(MessageType.OUTGOING);
        message.setTimestamp(TimestampUtil.getCurrentTimestamp());
        message.setContent(this.mBottomPanel.getText());
        this.mBottomPanel.setText("");
        this.updateMessageList(message);
        this.echoMessage(message);
    }

    private void echoMessage(final Message income) {
        new AsyncTask<Void, Void, Message>() {
            @Override
            protected void onPostExecute(Message message) {
                ActivityTelegram.this.updateMessageList(message);
            }

            @Override
            protected Message doInBackground(Void... params) {
                try {
                    Thread.sleep(1200);
                    Message outgoing = new Message();
                    outgoing.setType(MessageType.INCOME);
                    outgoing.setTimestamp(TimestampUtil.getCurrentTimestamp());
                    outgoing.setContent(income.getContent());
                    return outgoing;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void updateMessageList(Message message) {
        this.mAdapter.getDataset().add(message);
        this.mAdapter.notifyDataSetChanged();
        this.mMessages.scrollToPosition(this.mAdapter.getItemCount() - 1);
    }

    @Override
    public void onBackPressed() {
        if (this.mOnBackPressedListener != null) {
            if (!this.mOnBackPressedListener.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void setOnBackPressed(IOnBackPressedListener backListener) {
        this.mOnBackPressedListener = backListener;
    }
}
