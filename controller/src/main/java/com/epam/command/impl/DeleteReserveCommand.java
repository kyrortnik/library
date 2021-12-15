package com.epam.command.impl;

import com.epam.ReserveService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
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

public class DeleteReserveCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteReserveCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ReserveService reserveService = serviceFactory.getReserveService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in DeleteReserveCommand");

        Long userId = (Long) request.getSession().getAttribute(ID);
        String bookIdString = request.getParameter(BOOK_ID);

        controllerValidator.longValidation(userId);
        controllerValidator.numericParameterValidation(bookIdString);
        Long bookId = Long.valueOf(bookIdString);
        String lastCommand = "frontController?command=go_To_Page&address=main.jsp";
        String message;
        try {
            isValidUser(request);
            if (reserveService.delete(userId, bookId)) {
                message = "Reserve is deleted";
                processRequest(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                message = "Such reserve does not exist!";
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (ServiceException | ServletException | IOException e) {
            throw new ControllerException(e);
        }
    }
}
