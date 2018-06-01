package ru.aptech.library.controllers;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
            authors = authorDAO.find(
                    (SearchCriteriaAuthors) session.getAttribute("criteriaAuthors"),
                    authorsOnPage,
                    selectedPage,
                    (SortType) session.getAttribute("sortType")
            );
        } else {
            if(criteria.isEmpty() && session.getAttribute("criteriaAuthors")==null){
                SearchCriteriaAuthors sca = new SearchCriteriaAuthors();
                session.setAttribute("criteriaAuthors", sca);
                authors = authorDAO.find(sca, authorsOnPage, selectedPage, (SortType)session.getAttribute("sortType"));
            } else {
                session.setAttribute("criteriaAuthors", criteria);
                authors = authorDAO.find(criteria, authorsOnPage, selectedPage, (SortType)session.getAttribute("sortType"));
            }
        }

        modelAndView.addObject("criteriaAuthors", session.getAttribute("criteriaAuthors"));
        modelAndView.addObject("authorList", authors);
        modelAndView.addObject("sortTypeList", SortType.values());
        modelAndView.addObject("selectedPage", selectedPage);
        modelAndView.addObject("quantityAuthors", authorDAO.getQuantityAuthors((SearchCriteriaAuthors)session.getAttribute("criteriaAuthors")));
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
        boolean isAdded = saveAuthor(author, date);
        modelAndView.addObject("isAdded", isAdded);
        addAttributesForAddOrEditAuthor(modelAndView, author);
        return modelAndView;
    }

    @RequestMapping(value = "editAuthorView", method = RequestMethod.GET)
    public ModelAndView editAuthorView(@RequestParam Long authorId) {
        ModelAndView modelAndView = new ModelAndView("edit-author-page");
        addAttributesForAddOrEditAuthor(modelAndView, authorDAO.find(authorId));
        return modelAndView;
    }

    @RequestMapping(value = "editAuthorAction", method = RequestMethod.POST)
    public ModelAndView editAuthorAction(@ModelAttribute Author author, @RequestParam Long authorId, @RequestParam String date) {
        ModelAndView modelAndView = new ModelAndView("edit-author-page");
        boolean isEdited  = updateAuthor(author, authorId, date);
        modelAndView.addObject("isEdited", isEdited);
        addAttributesForAddOrEditAuthor(modelAndView, authorDAO.find(authorId));
        return modelAndView;
    }

    @RequestMapping(value = "deleteAuthor", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam(value = "authorId") Long authorId, HttpSession session) {
        boolean isDeleted = deleteAuthor(authorId);
        session.setAttribute("isDeleted", isDeleted);
        return "redirect:/authors/list";
    }

    private void addAttributesForAddOrEditAuthor(ModelAndView modelAndView, Author author) {
        String birthday = author.getBirthday() != null ? author.getBirthday().format(DATE_FORMAT) : "";
        modelAndView.addObject("author", author);
        modelAndView.addObject("bookList", bookDAO.find());
        modelAndView.addObject("date", birthday);
    }

    private boolean saveAuthor(Author author, String date){
        try {
            author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
            Set<Book> bookList = author.getBooks();
            Author savedAuthor = authorDAO.find(authorDAO.save(author));
            for(Book b : bookList) {
                b.setAuthor(savedAuthor);
                bookDAO.update(b);
            }
            //какая херня, но без нее почему-то селектор с книгами на странице add-or-edit-author.jsp очищается
            author.setAllField(authorDAO.find(savedAuthor.getId()));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private boolean updateAuthor(Author author, Long authorId, String date){
        try {
            author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
            Author existAuthor = authorDAO.find(authorId);
            Set<Book> newBookList = author.getBooks();
            Set<Book> oldBookList = existAuthor.getBooks();
            for(Book oB : oldBookList) {
                if(!newBookList.contains(oB)){
                    oB.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
                    bookDAO.update(oB);
                }
            }
            existAuthor.setAllField(author);
            authorDAO.update(existAuthor);
            for(Book nB : newBookList) {
                nB.setAuthor(existAuthor);
                bookDAO.update(nB);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private boolean deleteAuthor(Long authorId){
        try {
            for(Book b : authorDAO.find(authorId).getBooks()){
                b.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
                bookDAO.update(b);
            }
            authorDAO.delete(authorId);
            return true;
        } catch (Exception e){
            return false;
        }
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
                return bookId != null ? bookDAO.findWithContent(bookId) : null;
            }
        });
    }
}
