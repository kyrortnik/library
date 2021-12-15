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

import static com.epam.util.ControllerConstants.ID;
import static com.epam.util.ControllerConstants.PAGEABLE_ORDERS;

public class OrderInfoCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(OrderInfoCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in OrderInfoCommand");

        String lastCommand = defineLastCommand(request, true);
        String message = null;
        String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";
        Long userId = (Long) request.getSession().getAttribute(ID);
        controllerValidator.longValidation(userId);
        Long currentPage = getCurrentPage(request);
        Page<Book> pageOrder = new Page<>();
        pageOrder.setPageNumber(currentPage);

        try {
            isValidUser(request);
            pageOrder = bookService.getOrderedBooksPage(pageOrder, userId);
            controllerValidator.pageValidation(pageOrder);
            if (!pageOrder.getElements().isEmpty()) {
                request.setAttribute(PAGEABLE_ORDERS, pageOrder);
            } else {
                message = "No ordered books for you yet";
            }
            processRequest(request, lastCommand, message);
            request.getRequestDispatcher(pageForRedirect).forward(request, response);

        } catch (IOException | ServiceException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
