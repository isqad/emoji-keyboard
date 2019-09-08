package ru.funnyhourse.emojilibrary.model;

public class Emoji {
    private String emoji = "";

    private Emoji() {}

    static Emoji fromCodePoint(int codePoint) {
        Emoji emoji = new Emoji();
        emoji.emoji = newString(codePoint);
        return emoji;
    }

    static Emoji fromChar(char ch) {
        Emoji emoji = new Emoji();
        emoji.emoji = Character.toString(ch);
        return emoji;
    }

    static Emoji fromString(String chars) {
        Emoji emoji = new Emoji();
        emoji.emoji = chars;
        return emoji;
    }

    private static String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Emoji && emoji.equals(((Emoji) o).emoji);
    }

    @Override
    public int hashCode() {
        return emoji.hashCode();
    }

    public String getEmoji() {
        return emoji;
    }
}
