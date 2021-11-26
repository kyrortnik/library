package com.epam.command.impl;


import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.isNull;

public class ShowProductsCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowProductsCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ShowProductsCommand");
        try {
            Long currentPage = getCurrentPage(request);
            Page<Book> pageRequest = new Page<>();
            pageRequest.setPageNumber(currentPage);
            String lastCommand = defineLastCommand(request, true);
            String message;
            String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";

            Page<Book> page = bookService.getBooksPage(pageRequest);

            if (!page.getElements().isEmpty()) {
                request.setAttribute(PAGEABLE, page);
                successfulProcess(request, lastCommand, null);
            } else {
                message = "No books in library yet";
                unsuccessfulProcess(request, lastCommand, message);
            }
            request.getRequestDispatcher(pageForRedirect).forward(request, response);
        } catch (IOException | ServletException | ServiceException e) {
            throw new ControllerException(e);
        }

    }

    private Long getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (isNull(currentPageParam) || currentPageParam.isEmpty()) {
            currentPageParam = DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPageParam);
    }
}
