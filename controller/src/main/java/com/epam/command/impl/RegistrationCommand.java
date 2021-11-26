package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;
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

public class RegistrationCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class.getName());

    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService userService = factory.getUserService();
    private final ControllerValidator controllerValidator = new ControllerValidator();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in RegistrationCommand");
        String lastCommand;
        String message;

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String secondPassword = request.getParameter(SECOND_PASSWORD);
        controllerValidator.stringParameterValidation(login, password, secondPassword);

        User user = new User(login, password, USER);

        try {
            UserDTO registeredUser = userService.register(user, secondPassword);
            if (nonNull(registeredUser)) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = "Registration successful";
                request.getSession().setAttribute(USER, registeredUser.getLogin());
                request.getSession().setAttribute(ROLE, registeredUser.getRole());
                request.getSession().setAttribute(ID, registeredUser.getId());
                successfulProcessRedirect(request, response, lastCommand, message);
            } else {
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                message = "Registration failed. Check that login is not empty and two passwords match.";
                unsuccessfulProcess(request, response, lastCommand, message);

            }
        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
