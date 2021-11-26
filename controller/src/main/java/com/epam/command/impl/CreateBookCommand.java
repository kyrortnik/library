package com.epam.command.impl;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.validator.ControllerValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class CreateBookCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreateBookCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in CreateBookCommand");

        String lastCommand = "frontController?command=go_To_Page&address=newBook.jsp";
        String message;
        try {
            Book book = getBookFromClient(request);
            if (bookService.create(book)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Book is created";
                successfulProcessRedirect(request, response, lastCommand, message);
            } else {
                message = "Book with such title already exists!";
                unsuccessfulProcess(request, response, lastCommand, message);
            }
        } catch (Exception e) {
            try {
                request.getSession().setAttribute(MESSAGE, "Error: " + e.getMessage());
                request.getSession().setAttribute(LAST_COMMAND, "frontController?command=go_To_Page&address=newBook.jsp");
                request.getRequestDispatcher(lastCommand).forward(request, response);
                LOG.info("Exception: " + e);

            } catch (ServletException | IOException ex) {
                throw new ControllerException(ex);
            }
            throw new ControllerException(e);

        }
    }

    private Book getBookFromClient(HttpServletRequest request) throws ControllerException {

        String title = request.getParameter(TITLE);
        String author = request.getParameter(AUTHOR);
        String publisher = request.getParameter(PUBLISHER);
        String genre = request.getParameter(GENRE);
        String description = request.getParameter(DESCRIPTION);
        boolean isHardCover = request.getParameter(IS_HARD_COVER).toUpperCase(Locale.ROOT).equals(YES);
        String pages = request.getParameter(NUMBER_OF_PAGES);
        String publishYear = request.getParameter(PUBLISHING_YEAR);

        controllerValidator.stringParameterValidation(title, author, publisher, genre, description);
        controllerValidator.numericParameterValidation(pages, publishYear);
        int numberOfPages = Integer.parseInt(pages);
        int publishingYear = Integer.parseInt(publishYear);

        return new Book(title, author, publishingYear, publisher, genre, numberOfPages, isHardCover, description);
    }

}


