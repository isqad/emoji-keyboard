# Android Emoji Keyboard

A library to provide an emoji-keyboard implementation for Android applications

<p align="center">
<img src="showcase/animation.gif" align="center"  hspace="20">
</p>

# Quickly Setup

```
dependencies {
    compile 'ru.funnyhourse:emoji-library:1.0.0'
}
```

Then add to your layout:

```xml
<ru.funnyhourse.emojilibrary.view.EmojiEditTextPanel
                android:id="@+id/bottompanel"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_alignParentBottom="true"/>
```

Initialize presenter in your activity/fragment:

```java
public class SmartChatActivity extends AppCompatActivity implements EmojiEditTextPanelEventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.smartchat);

        EmojiEditTextPanel editTextPanel = (EmojiEditTextPanel) findViewById(R.id.bottompanel);
        presenter = EmojiEditTextPanelPresenter.newInstance(editTextPanel, getSupportFragmentManager());
        presenter.setEventListener(this);
    }

    @Override
    public void onAttachClicked() {
        Log.i(TAG, "Attach was clicked");
    }

    @Override
    public void onMicClicked() {
        Log.i(TAG, "Mic was clicked");
    }

    @Override
    public void onSendClicked() {
        Log.i(TAG, "Send was clicked");
    }
```

# TODO

* More tests
* Add onBackClickListener for closing keyboard
* Configure EmojiEditTextPanel
* Documentation

# License

Copyright 2019 Andrew Shalaev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
