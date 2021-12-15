package com.epam.command.impl;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static com.epam.util.ControllerConstants.BOOK_ID;

public class DeleteBookCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteBookCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in DeleteBookCommand");
        String lastCommand;
        String message;
        try {

            Long bookId = Long.valueOf(request.getParameter(BOOK_ID));
            controllerValidator.longValidation(bookId);

            if (bookService.delete(bookId)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Book is deleted";
                successfulProcess(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                lastCommand = "frontController?command=product_Info?id=" + bookId;
                message = "No such book exists";
                unsuccessfulProcess(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (IOException | ServiceException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
