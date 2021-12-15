package com.epam.command.impl;


import com.epam.BookService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.PAGEABLE;

public class ShowProductsCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowProductsCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ShowProductsCommand");
        try {
            isValidUser(request);
            Long currentPage = getCurrentPage(request);
            Page<Book> pageRequest = new Page<>();
            pageRequest.setPageNumber(currentPage);
            String lastCommand = defineLastCommand(request, true);
            String message = null;
            String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";

            Page<Book> page = bookService.getBooksPage(pageRequest);

            if (!page.getElements().isEmpty()) {
                request.setAttribute(PAGEABLE, page);
            } else {
                message = "No books in library yet";
            }
            processRequest(request, lastCommand, message);
            request.getRequestDispatcher(pageForRedirect).forward(request, response);
        } catch (IOException | ServletException | ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
