package ru.aptech.library.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.util.List;

public interface CommonDAO<T> extends DAO<T, Long> {

    @Transactional
    default T find(String uniqName){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default List<T> find(Integer onPage, Integer first, SortType sortType){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default List<T> find(SearchCriteriaBooks criteria, Integer onPage, Integer first, SortType sortType){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default List<T> find(SearchCriteriaAuthors criteria, Integer onPage, Integer first, SortType sortType){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default T findWithoutContent(Long id){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default byte[] findBookImage(Long id){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default void increaseView(Long id){throw new UnsupportedOperationException();}

    @Transactional
    default Long getQuantity(){throw new UnsupportedOperationException();}

    @Transactional
    default Long getQuantity(SearchCriteriaBooks criteria){throw new UnsupportedOperationException();}

    @Transactional
    default Long getQuantity(SearchCriteriaAuthors criteria){throw new UnsupportedOperationException();}

    @Transactional
    default void setViews(Long authorId, Long views){throw new UnsupportedOperationException();}
}
