package ru.funnyhourse.emojilibrary.presenter;

import java.util.ArrayList;

import ru.funnyhourse.emojilibrary.model.Emoji;

public interface IRecentEmojiSaver {
    void addRecentEmoji(Emoji recentEmoji);
    ArrayList<Emoji> getRecentEmojis();
}
