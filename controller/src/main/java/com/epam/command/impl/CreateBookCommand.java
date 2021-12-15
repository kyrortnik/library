package com.epam.command.impl;

import com.epam.BookService;
import com.epam.factory.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.validator.ControllerValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static com.epam.util.ControllerConstants.*;

public class CreateBookCommand extends AbstractCommand implements Command {


    private static final Logger LOG = Logger.getLogger(CreateBookCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();
//    private final ControllerValidator controllerValidator = ControllerValidator.getInstance();


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
//                successfulProcess(request, lastCommand, message);
                processRequest(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                message = "Book with such title already exists!";
//                unsuccessfulProcess(request, lastCommand, message);
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (Exception e) {
            try {
                request.getSession().setAttribute(MESSAGE, e.getMessage());
                request.getSession().setAttribute(LAST_COMMAND, "frontController?command=go_To_Page&address=newBook.jsp");
                request.getRequestDispatcher(lastCommand).forward(request, response);
                LOG.error(e.getMessage());
            } catch (ServletException | IOException ex) {
                throw new ControllerException(ex);
            }
            throw new ControllerException(e);

        }
    }

    private Book getBookFromClient(HttpServletRequest request) throws ControllerException {

        String title = request.getParameter(TITLE).trim();
        String author = request.getParameter(AUTHOR).trim();
        String publisher = request.getParameter(PUBLISHER).trim();
        String genre = request.getParameter(GENRE).trim();
        String description = request.getParameter(DESCRIPTION).trim();
        boolean isHardCover = Boolean.parseBoolean(request.getParameter(IS_HARD_COVER));
        String pages = "".equals(request.getParameter(NUMBER_OF_PAGES)) ? "0" : request.getParameter(NUMBER_OF_PAGES).trim();
        String publishYear = "".equals(request.getParameter(PUBLISHING_YEAR)) ? "0" : request.getParameter(PUBLISHING_YEAR).trim();

        controllerValidator.stringParameterValidation(title, author);
        controllerValidator.stringParameterValidationNonNull(publisher, genre, description);

        controllerValidator.numericParameterValidation(pages, publishYear);
        int numberOfPages = Integer.parseInt(pages);
        int publishingYear = Integer.parseInt(publishYear);

        return new Book(title, author, publishingYear, publisher, genre, numberOfPages, isHardCover, description);
    }

}


