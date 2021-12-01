package com.epam.command.impl;

import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.nonNull;


public class LoginCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);


        String lastCommand;
        String message;

        try {
            LOG.info("Start in LoginCommand");
            UserDTO foundUser = userService.login(login, password);
            if (nonNull(foundUser)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Hello," + login + " !";
                request.getSession().setAttribute(USER, foundUser.getLogin());
                request.getSession().setAttribute(ROLE, foundUser.getRole());
                request.getSession().setAttribute(ID, foundUser.getId());
                successfulProcess(request, lastCommand, message);
                response.sendRedirect(lastCommand);
            } else {
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                message = "Incorrect login or password.Try again";
                unsuccessfulProcess(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            }
        } catch (IOException | ServiceException | ServletException e) {
            try {
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                message = "Login and password can`t be empty!";
                unsuccessfulProcess(request, lastCommand, message);
                request.getRequestDispatcher(lastCommand).forward(request, response);
            } catch (IOException | ServletException ex) {
                throw new ControllerException(ex);
            }
            throw new ControllerException(e);
        }

    }
}