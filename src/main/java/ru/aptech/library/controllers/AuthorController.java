package ru.aptech.library.controllers;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.Author;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("authors/")
public class AuthorController extends BaseController{

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView authorList(@RequestParam(required = false) Integer authorsOnPage,
                                   @RequestParam(required = false) Integer selectedPage,
                                   @RequestParam(required = false) Boolean isPagination,
                                   @ModelAttribute("criteriaAuthors") SearchCriteriaAuthors criteria,
                                   HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("author-page-list-author");
        if (selectedPage == null) {
            selectedPage = 1;
        }
        if (authorsOnPage == null && session.getAttribute("authorsOnPage") == null) {
            authorsOnPage = PAGE_SIZE_VALUE;
            session.setAttribute("authorsOnPage", authorsOnPage);
        } else if (authorsOnPage == null && session.getAttribute("authorsOnPage") != null) {
            authorsOnPage = (Integer)session.getAttribute("authorsOnPage");
        } else {
            session.setAttribute("authorsOnPage", authorsOnPage);
        }

        List<Author> authors;
        if(isPagination!=null && isPagination){
            authors = authorService.find(
                    (SearchCriteriaAuthors) session.getAttribute("criteriaAuthors"),
                    authorsOnPage,
                    selectedPage,
                    (SortType) session.getAttribute("sortType"),
                    true
            );
        } else {
            if(criteria.isEmpty() && session.getAttribute("criteriaAuthors")==null){
                SearchCriteriaAuthors sca = new SearchCriteriaAuthors();
                session.setAttribute("criteriaAuthors", sca);
                authors = authorService.find(sca, authorsOnPage, selectedPage, (SortType)session.getAttribute("sortType"), true);
            } else {
                session.setAttribute("criteriaAuthors", criteria);
                authors = authorService.find(criteria, authorsOnPage, selectedPage, (SortType)session.getAttribute("sortType"), true);
            }
        }

        modelAndView.addObject("criteriaAuthors", session.getAttribute("criteriaAuthors"));
        modelAndView.addObject("authorList", authors);
        modelAndView.addObject("sortTypeList", SortType.values());
        modelAndView.addObject("selectedPage", selectedPage);
        modelAndView.addObject("quantityAuthors", authorService.getQuantityAuthors((SearchCriteriaAuthors)session.getAttribute("criteriaAuthors")));
        return modelAndView;
    }

    @RequestMapping(value = "addAuthorView", method = RequestMethod.GET)
    public ModelAndView addAuthorView() {
        ModelAndView modelAndView = new ModelAndView("add-author-page");
        addAttributesForAddOrEditAuthor(modelAndView, new Author());
        return modelAndView;
    }

    @RequestMapping(value = "addAuthorAction", method = RequestMethod.POST)
    public ModelAndView addAuthorAction(@ModelAttribute Author author, @RequestParam String date, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("add-author-page");
        boolean isAdded;
        Long savedAuthorId;
        try {
            savedAuthorId = authorService.save(author, date);
            isAdded = true;
        } catch (Exception e){
            savedAuthorId = null;
            isAdded = false;
            e.printStackTrace();
        }
        modelAndView.addObject("isAdded", isAdded);
        addAttributesForAddOrEditAuthor(modelAndView, savedAuthorId == null ? author : authorService.find(savedAuthorId, true));
        return modelAndView;
    }

    @RequestMapping(value = "editAuthorView", method = RequestMethod.GET)
    public ModelAndView editAuthorView(@RequestParam Long authorId) {
        ModelAndView modelAndView = new ModelAndView("edit-author-page");
        addAttributesForAddOrEditAuthor(modelAndView, authorService.find(authorId, true));
        return modelAndView;
    }

    @RequestMapping(value = "editAuthorAction", method = RequestMethod.POST)
    public ModelAndView editAuthorAction(@ModelAttribute Author author, @RequestParam Long authorId, @RequestParam String date) {
        ModelAndView modelAndView = new ModelAndView("edit-author-page");
        boolean isEdited;
        try {
            authorService.update(author, authorId, date);
            isEdited = true;
        } catch (Exception e){
            isEdited = false;
            e.printStackTrace();
        }
        modelAndView.addObject("isEdited", isEdited);
        addAttributesForAddOrEditAuthor(modelAndView, authorService.find(authorId, true));
        return modelAndView;
    }

    @RequestMapping(value = "deleteAuthor", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam(value = "authorId") Long authorId, HttpSession session) {
        boolean isDeleted;
        try {
            authorService.delete(authorId);
            isDeleted = true;
        } catch (Exception e){
            isDeleted = false;
            e.printStackTrace();
        }
        session.setAttribute("isDeleted", isDeleted);
        return "redirect:/authors/list";
    }

    private void addAttributesForAddOrEditAuthor(ModelAndView modelAndView, Author author) {
        String birthday = author.getBirthday() != null ? author.getBirthday().format(DATE_FORMAT) : "";
        modelAndView.addObject("author", author);
        modelAndView.addObject("bookList", bookService.find(false));
        modelAndView.addObject("date", birthday);
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "books", new CustomCollectionEditor(Set.class)
        {
            @Override
            protected Object convertElement(Object element) {
                Long bookId = null;
                if (element instanceof String) {
                    bookId = Long.parseLong(element.toString());
                } else if (element instanceof Long) {
                    bookId = (Long) element;
                }
                return bookId != null ? bookService.find(bookId, false, true) : null;
            }
        });
    }
}
