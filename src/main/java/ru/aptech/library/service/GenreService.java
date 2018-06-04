package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.GenreDAOImpl;
import ru.aptech.library.entities.Genre;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    protected GenreDAOImpl genreDAO;


    public List<Genre> find() {
        try {
            return genreDAO.find();
        }catch (Exception e){
            return null;
        }
    }


    public Genre find(Long id) {
        try {
            return genreDAO.find(id);
        }catch (Exception e){
            return null;
        }
    }

}
