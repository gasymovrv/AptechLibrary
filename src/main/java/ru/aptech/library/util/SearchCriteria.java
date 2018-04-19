package ru.aptech.library.util;


import ru.aptech.library.enums.SearchType;

import java.io.Serializable;

public class SearchCriteria implements Serializable{

    private String text;

    private SearchType searchType = SearchType.TITLE;

    private Character letter;

    private Long genreId;


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

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
