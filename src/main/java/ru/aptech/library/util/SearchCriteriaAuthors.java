package ru.aptech.library.util;


import org.springframework.util.StringUtils;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.enums.SortType;

import java.io.Serializable;

public class SearchCriteriaAuthors implements Serializable{

    private String text;

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


    public boolean isEmpty(){
        return getText() == null;
    }
}
