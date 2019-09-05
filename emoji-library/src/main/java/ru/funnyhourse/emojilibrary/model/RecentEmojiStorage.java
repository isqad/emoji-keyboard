package ru.funnyhourse.emojilibrary.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// TODO: save to shared preferences
public final class RecentEmojiStorage {
    private static final Deque<Emoji> emojis = new ArrayDeque<>();

    private RecentEmojiStorage() {}

    public static void addRecentEmoji(Emoji emoji) {
        emojis.addFirst(emoji);
    }

    public static List<Emoji> getRecentEmojis() {
        return new ArrayList<>(emojis);
    }
}
