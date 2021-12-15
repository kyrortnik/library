package com.epam.command.impl;

import com.epam.ReserveService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.entity.Reserve;
import com.epam.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.BOOK_ID;
import static com.epam.util.ControllerConstants.ID;

public class CreateReserveCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreateReserveCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ReserveService reserveService = serviceFactory.getReserveService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in CreateReserveCommand");
        try {
            isValidUser(request);
            Long userId = (Long) request.getSession().getAttribute(ID);
            controllerValidator.longValidation(userId);

            String bookIdString = request.getParameter(BOOK_ID);
            controllerValidator.numericParameterValidation(bookIdString);
            Long bookId = Long.valueOf(bookIdString);

            Reserve reserve = new Reserve(userId, bookId);
            String lastCommand = "frontController?command=go_To_Page&address=main.jsp";
            String message;
            if (reserveService.save(reserve)) {
                message = "Book is reserved";
                processRequest(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                message = "Product is not added to Reserve list! Reserve for this product already exists.";
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}