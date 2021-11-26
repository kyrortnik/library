package com.epam.command.impl;

import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.nonNull;


public class LoginCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    private static final Logger LOG = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        controllerValidator.stringParameterValidation(login, password);

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
            throw new ControllerException(e);
        }

    }
}