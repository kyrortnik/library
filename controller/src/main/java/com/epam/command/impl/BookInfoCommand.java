package com.epam.command.impl;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static com.epam.util.ControllerConstants.*;

public class BookInfoCommand extends AbstractCommand implements Command {


    private static final Logger LOG = Logger.getLogger(BookInfoCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ProductInfoCommand");

        String userId = request.getParameter(ID);
        controllerValidator.numericParameterValidation(userId);

        String lastCommand = defineLastCommand(request, false);
        String message;

        try {
            Book book = bookService.findById(Long.parseLong(userId));

            if (book != null) {
                message = (String) request.getSession().getAttribute(MESSAGE);
                request.setAttribute(BOOK, book);
                successfulProcess(request, lastCommand, message);
            } else {
                message = "No such product was found";
                unsuccessfulProcess(request, lastCommand, message);
            }
            request.getRequestDispatcher("frontController?command=go_To_Page&address=productInfo.jsp").forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
