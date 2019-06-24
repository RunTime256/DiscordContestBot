package me.spider.contestbot.contestcommand.structures;

public enum  Category {
    EMOJI("Emoji"), ART("Art"), MUSIC("Music"), WRITING("Music");
    private final String categoryStr;
    Category(final String categoryStr){
        this.categoryStr = categoryStr;
    }

    public String getCategoryStr() {
        return categoryStr;
    }


}
