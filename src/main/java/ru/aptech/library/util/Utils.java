package ru.aptech.library.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.aptech.library.enums.SearchType;

import java.util.*;

@Component
public class Utils {

    private List<SearchType> searchTypeList = new ArrayList<SearchType>();
    private SearchType selectedSearchType = SearchType.TITLE;// значение по-умолчанию


    @Autowired
    private MessageSource msg;

    private Character[] letters = new Character[]{'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};

    public Character[] getLetters() {
        return letters;
    }

    public List<SearchType> getSearchTypeList() {
        searchTypeList.clear();
        searchTypeList.addAll(Arrays.asList(SearchType.values()));
        searchTypeList.remove(selectedSearchType);
        return searchTypeList;
    }

    public SearchType getSelectedSearchType() {
        return selectedSearchType;
    }



}
