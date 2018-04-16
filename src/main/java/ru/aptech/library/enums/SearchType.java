package ru.aptech.library.enums;

public enum SearchType {
    TITLE("По названию"),
    AUTHOR("По автору"),
    GENRE("По жанру"),
    PUBLISHER("По издательству");

    private String text;


    SearchType(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }
}
