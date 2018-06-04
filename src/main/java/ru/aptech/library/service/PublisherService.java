package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.PublisherDAOImpl;
import ru.aptech.library.entities.Publisher;

import java.util.List;

@Service
public class PublisherService {
    @Autowired
    private PublisherDAOImpl publisherDAO;


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
