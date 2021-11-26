package com.epam.command.impl;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.BOOK_ID;
import static com.epam.util.ControllerConstants.ID;

public class DeleteBookFromOrderCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteBookFromOrderCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private final ControllerValidator controllerValidator = new ControllerValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in DeleteProductFromOrder");

        Long userId = (Long) request.getSession().getAttribute(ID);
        String bookIdString = request.getParameter(BOOK_ID);

        controllerValidator.validation(userId);
        controllerValidator.numericParameterValidation(bookIdString);
        Long bookId = Long.parseLong(bookIdString);

        String lastCommand;
        String message;
        try {
            if (orderService.deleteFromOrder(userId,bookId)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Book is deleted from order";
                successfulProcessRedirect(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                lastCommand = "frontController?command=order_Info";
                message = "Book wasn't deleted from order";
                unsuccessfulProcess(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        } catch (Exception e) {
            throw new ControllerException(e);
        }

    }
}

