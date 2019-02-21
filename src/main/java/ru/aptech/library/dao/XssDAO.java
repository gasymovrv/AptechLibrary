package ru.aptech.library.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.XssData;

import java.util.List;

public interface XssDAO {
    @Transactional
    void save(XssData xssData);

}
