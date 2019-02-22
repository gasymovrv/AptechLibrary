package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.aptech.library.dao.XssDAO;
import ru.aptech.library.entities.XssData;

import java.sql.Timestamp;
import java.util.Date;


@Controller
public class XssController{
    @Autowired
    private XssDAO xssDAO;
    @RequestMapping(value = "/xss", method = RequestMethod.GET)
    public String saveXss(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "data", required = false) String data
    ) {
        XssData xssData = new XssData();
        xssData.setLocation(location);
        xssData.setData(data);
        xssData.setCreated(new Timestamp(new Date().getTime()));
        xssDAO.save(xssData);
        String url = "https://www.google.com";
        if(!StringUtils.isEmpty(location) && location.startsWith("http")){
            url = location;
            if(url.contains("?")){
                url+="&xssredirect=1";
            } else {
                url+="?xssredirect=1";
            }
        }
        return "redirect:"+url;
    }

    //Не работает из-за CORS запрета на ajax
    @RequestMapping(value = "/xss-by-ajax", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveXssByAjax(@RequestBody XssData xssData) {
        xssDAO.save(xssData);
    }
}
