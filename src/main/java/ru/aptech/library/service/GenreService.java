package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.impl.GenreDAOImpl;
import ru.aptech.library.entities.Genre;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    @Qualifier("genreDAO")
    private CommonDAO<Genre> genreDAO;


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
