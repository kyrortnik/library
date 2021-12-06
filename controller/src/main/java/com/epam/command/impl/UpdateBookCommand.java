package com.epam.command.impl;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.validator.ControllerValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.*;

public class UpdateBookCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateBookCommand.class);

    private final BookService bookService = ServiceFactory.getInstance().getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in UpdateBookCommand");

        String bookIdString = request.getParameter(BOOK_ID);
        controllerValidator.numericParameterValidation(bookIdString);
        long bookId = Long.parseLong(bookIdString);
        String lastCommand = "frontController?command=book_Info&id=" + bookId;
//        String lastCommand = "frontController?command=go_To_Page&address=productInfo.jsp&id=" + bookId;
        String message;

        try {
            Book book = getBookClient(request);
            if (bookService.update(book)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Book is updated";
                successfulProcess(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                message = "No such book exists";
                unsuccessfulProcess(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (Exception e) {
            try {
                request.getSession().setAttribute(MESSAGE,e.getMessage());
                request.getSession().setAttribute(LAST_COMMAND, "frontController?command=go_To_Page&address=productInfo.jsp&id=" + bookId);
                request.getRequestDispatcher(lastCommand).forward(request, response);
                LOG.error(e.getMessage());
            } catch (IOException | ServletException ex) {
                throw new ControllerException(ex);
            }
            throw new ControllerException(e);
        }
    }

    private Book getBookClient(HttpServletRequest request) throws ControllerException {

        String idString = request.getParameter(BOOK_ID).trim();
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

        controllerValidator.numericParameterValidation(idString, pages, publishYear);
        Long id = Long.parseLong(idString);
        int numberOfPages = Integer.parseInt(pages);
        int publishingYear = Integer.parseInt(publishYear);

        return new Book(id, title, author, publishingYear, publisher, genre, numberOfPages, isHardCover, description);
    }

}
