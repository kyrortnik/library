package com.epam.command.impl;

import com.epam.factory.ServiceFactory;
import com.epam.UserService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.nonNull;

public class RegistrationCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService userService = factory.getUserService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in RegistrationCommand");
        String lastCommand;
        String message;
        request.getSession().setAttribute(LAST_COMMAND, "frontController?command=go_To_Page$address=login.jsp");

        String login = request.getParameter(LOGIN);
        char[] password = request.getParameter(PASSWORD).toCharArray();
        char[] secondPassword = request.getParameter(SECOND_PASSWORD).toCharArray();

        User user = new User(login, password, USER);

        try {
            UserDTO registeredUser = userService.register(user, secondPassword);
            if (nonNull(registeredUser)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Registration successful";
                request.getSession().setAttribute(USER, registeredUser.getLogin());
                request.getSession().setAttribute(ROLE, registeredUser.getRole());
                request.getSession().setAttribute(ID, registeredUser.getId());
                processRequest(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                message = "Registration failed. Check that login is not empty and two passwords match.";
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);

            }
        } catch (ServiceException | IOException | ServletException e) {
            try {
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                message = "Login and passwords are empty or user already exists.";
                processRequest(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            } catch (IOException | ServletException ex) {
                throw new ControllerException(ex);
            }
            throw new ControllerException(e);
        }
    }
}
