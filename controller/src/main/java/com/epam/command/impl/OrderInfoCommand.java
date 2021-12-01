package com.epam.command.impl;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.epam.util.ControllerConstants.*;

public class OrderInfoCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(OrderInfoCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in OrderInfoCommand");

        String lastCommand = defineLastCommand(request, true);
        String message;
        String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";
        Long userId = (Long) request.getSession().getAttribute(ID);
        controllerValidator.longValidation(userId);
        Long currentPage = getCurrentPage(request);
        Page<Book> pageOrder = new Page<>();
        pageOrder.setPageNumber(currentPage);

        try {
            pageOrder = bookService.getOrderedBooksPage(pageOrder, userId);
            controllerValidator.pageValidation(pageOrder);
            if (!pageOrder.getElements().isEmpty()) {
                request.setAttribute(PAGEABLE_ORDERS, pageOrder);
                successfulProcess(request, lastCommand, null);
            } else {
                message = "No ordered books for you yet.";
                unsuccessfulProcess(request, lastCommand, message);
            }
            request.getRequestDispatcher(pageForRedirect).forward(request, response);

        } catch (IOException | ServiceException | ServletException e) {
            throw new ControllerException(e);
        }


    }

    private Long getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE_RESERVE);
        if (Objects.isNull(currentPageParam)) {
            currentPageParam = DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPageParam);
    }

}
