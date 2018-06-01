package ru.aptech.library.util;


import org.springframework.util.StringUtils;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.enums.SortType;

import java.io.Serializable;

public class SearchCriteriaBooks implements Serializable{

    private String text;

    private SearchType searchType = SearchType.TITLE;

    private Character letter;

    private Long genreId;

    private Long publisherId;

    private Long authorId;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(StringUtils.isEmpty(text)){
            this.text = null;
        } else {
            this.text = text;
        }
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


    public Long getPublisherId() {
        return publisherId;
    }


    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }


    public Long getAuthorId() {
        return authorId;
    }


    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public boolean isEmpty(){
        return getText() == null
                && getAuthorId() == null
                && getGenreId() == null
                && getLetter() == null
                && getPublisherId() == null;
    }
}
