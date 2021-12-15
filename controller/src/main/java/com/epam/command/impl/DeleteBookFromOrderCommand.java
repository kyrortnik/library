package com.epam.command.impl;

import com.epam.OrderService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import com.epam.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.util.ControllerConstants.BOOK_ID;
import static com.epam.util.ControllerConstants.ID;

public class DeleteBookFromOrderCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteBookFromOrderCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in DeleteProductFromOrder");

        Long userId = (Long) request.getSession().getAttribute(ID);
        String bookIdString = request.getParameter(BOOK_ID);

        controllerValidator.longValidation(userId);
        controllerValidator.numericParameterValidation(bookIdString);
        Long bookId = Long.parseLong(bookIdString);
        String lastCommand;
        String message;
        try {
            isValidUser(request);
            if (orderService.deleteFromOrder(userId, bookId)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Book is deleted from order";
                processRequest(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                lastCommand = "frontController?command=order_Info";
                message = "Book wasn't deleted from order";
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (Exception e) {
            throw new ControllerException(e);
        }
    }
}

