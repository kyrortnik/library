package com.epam.command.impl;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.util.ControllerConstants.ID;
import static com.epam.util.ControllerConstants.RESERVED_BOOKS;

public class CreateOrderCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(CreateOrderCommand.class.getName());
    private static final String BOOK_ID_FINDER_PATTERN = "(?<=id=)\\d*(?=\\,)";

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private final ControllerValidator controllerValidator = new ControllerValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in CreateOrderCommand");
        try {
            Long userId = (Long) request.getSession().getAttribute(ID);
            controllerValidator.validation(userId);
            List<Long> bookIds = getReservedBookIds(request);
            String lastCommand = "frontController?command=go_To_Page&address=main.jsp";
            if (orderService.createByUserId(userId, bookIds)) {
                successfulProcess(request, response, lastCommand, "Products ordered! To see the click to Order List");
            } else {
                unsuccessfulProcess(request, response, lastCommand, "Couldn't create or update existing order");
            }
        } catch (Exception e) {
            throw new ControllerException(e);
        }

    }

    private List<Long> getReservedBookIds(HttpServletRequest request) throws ControllerException {

        List<Long> bookIds = new ArrayList<>();
        String reservedBooks = request.getParameter(RESERVED_BOOKS);
        controllerValidator.stringParameterValidation(reservedBooks);

        Pattern pattern = Pattern.compile(BOOK_ID_FINDER_PATTERN);
        Matcher matcher = pattern.matcher(reservedBooks);
        while (matcher.find()) {
            bookIds.add(Long.parseLong(matcher.group()));
        }
        return bookIds;
    }
}





