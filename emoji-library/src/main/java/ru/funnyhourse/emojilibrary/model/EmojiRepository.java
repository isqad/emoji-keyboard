package ru.funnyhourse.emojilibrary.model;

/**
 * Singleton for Emojies
 */
public enum EmojiRepository {
    INSTANCE;

    private EmojiCategory[] categories = {
            new People(),
            new Nature(),
            new Objects(),
            new Places(),
            new Symbols()
    };

    public EmojiCategory at(int pos) {
        return categories[pos];
    }

    public int size() {
        return categories.length;
    }

    public EmojiCategory[] getCategories() {
        return categories;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
