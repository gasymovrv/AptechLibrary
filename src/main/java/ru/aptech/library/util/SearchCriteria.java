package ru.aptech.library.util;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.enums.SearchType;

import java.io.Serializable;

public class SearchCriteria implements Serializable{

    private String text;

    private SearchType searchType;

    private Character letter;

    private Genre genre;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
