package com.epam.command.impl;


import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.entity.Page;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class ShowProductsCommand extends AbstractCommand implements Command {

    private final BookService bookService = ServiceFactory.getInstance().getBookService();
    private static final Logger LOG = Logger.getLogger(ShowProductsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ShowProductsCommand");
        try {
            int currentPage = getCurrentPage(request);
            Page<Book> pageableRequest = new Page<>();
            pageableRequest.setPageNumber(currentPage);
            String lastCommand = defineLastCommand(request, true);
            request.getSession().setAttribute(LAST_COMMAND, lastCommand);
            request.getSession().setAttribute(MESSAGE, null);

            Page<Book> pageable = bookService.getAll(pageableRequest);

            if (!pageable.getElements().isEmpty()) {
                request.setAttribute("pageable", pageable);
            } else {
                request.setAttribute("productsMessage", "No books in library yet");
            }
            request.getRequestDispatcher("frontController?command=go_To_Page&address=main.jsp").forward(request, response);
        } catch (IOException | ServletException | ServiceException e) {
            throw new ControllerException(e);
        }


    }

    private int getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (Objects.isNull(currentPageParam)) {
            currentPageParam = "1";
        }
        return Integer.parseInt(currentPageParam);
    }
}
