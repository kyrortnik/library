package com.epam.command;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class CreateReserveCommand extends AbstractCommand {

    private final ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

    private static final Logger log = Logger.getLogger(CreateReserveCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateReserveCommand");
        try {
            Long userId = (Long) request.getSession().getAttribute(ID);
            Long bookId = Long.valueOf(request.getParameter("bookId"));
            Reserve reserve = new Reserve(userId, bookId);
            String lastCommand;
                if (reserveService.save(reserve)) {
                    lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                    request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                    request.getSession().setAttribute(MESSAGE,"Book is reserved");
                    response.sendRedirect(lastCommand);
                } else {
                    request.getSession().setAttribute(MESSAGE,"Product is not added to Reserve list! Order for this product already exists.");
                    lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                    request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                    request.getRequestDispatcher(lastCommand).forward(request,response);
                }
        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);

        }
    }

}