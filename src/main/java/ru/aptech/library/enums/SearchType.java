package ru.aptech.library.enums;

public enum SearchType {
    TITLE("TITLE", "По названию"),
    AUTHOR("AUTHOR", "По автору"),
    GENRE("GENRE", "По жанру"),
    PUBLISHER("PUBLISHER", "По издательству");

    private String id;


    private String text;


    SearchType(String id, String text) {
        this.text = text;
    }


    public String getId() {
        return id;
    }


    public String getText() {
        return text;
    }
}
