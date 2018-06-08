package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.impl.PublisherDAOImpl;
import ru.aptech.library.entities.Publisher;

import java.util.List;

@Service
public class PublisherService {
    @Autowired
    @Qualifier("publisherDAO")
    private CommonDAO<Publisher> publisherDAO;


    public List<Publisher> find() {
        try {
            return publisherDAO.find();
        }catch (Exception e){
            return null;
        }
    }


    public Publisher find(Long id) {
        try {
            return publisherDAO.find(id);
        }catch (Exception e){
            return null;
        }
    }

}
