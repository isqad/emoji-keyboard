package ru.funnyhourse.emojilibrary.model.layout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by edgar on 18/02/2016.
 */
public class EmojiCompatActivity extends AppCompatActivity implements IEmojiActivity {

    private IOnBackPressedListener mOnBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
