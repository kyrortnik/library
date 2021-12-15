package com.epam.command.impl;

import com.epam.UserService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.entity.Page;
import com.epam.entity.UserDTO;
import com.epam.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.LOGIN;
import static com.epam.util.ControllerConstants.PAGEABLE_USERS;


public class ShowUsersCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowUsersCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in ShowUsersCommand");

        try {
            isValidUser(request);
            isValidAdminUser(request);
            Long currentPage = getCurrentPage(request);
            String lastCommand = defineLastCommand(request, true);
            String message = null;
            String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";
            Page<UserDTO> pageableRequest = new Page<>();

            pageableRequest.setPageNumber(currentPage);
            pageableRequest.setSortBy(LOGIN);
            Page<UserDTO> usersPage = userService.getUsersPage(pageableRequest);

            if (!usersPage.getElements().isEmpty()) {
                request.setAttribute(PAGEABLE_USERS, usersPage);
            } else {
                message = "Users were not found";
            }
            processRequest(request,lastCommand,message);
            request.getRequestDispatcher(pageForRedirect).forward(request, response);

        } catch (IOException | ServletException | ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
