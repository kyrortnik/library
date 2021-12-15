package com.epam.command.impl;

import com.epam.BookService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.entity.Book;
import com.epam.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.nonNull;

public class BookInfoCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(BookInfoCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ProductInfoCommand");

        String userId = request.getParameter(ID);
        controllerValidator.numericParameterValidation(userId);
        String lastCommand = defineLastCommand(request, false);
        String message;

        try {
            isValidUser(request);
            Book book = bookService.findById(Long.parseLong(userId));

            if (nonNull(book)) {
                message = (String) request.getSession().getAttribute(MESSAGE);
                request.setAttribute(BOOK, book);
            } else {
                message = "No such product was found";
            }
            processRequest(request, lastCommand, message);
            request.getRequestDispatcher("frontController?command=go_To_Page&address=productInfo.jsp").forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
