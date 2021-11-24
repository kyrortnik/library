package com.epam.command.impl;

import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.security.Salt;
import com.epam.validator.ControllerValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;


public class LoginCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    private static final Logger LOG = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        controllerValidator.stringParameterValidation(login,password);

        User user = new User(login);
        String lastCommand;

        try{
            LOG.info("Start in LoginCommand");
            User foundUser = userService.logination(user);
            if (foundUser != null && Salt.verifyPassword(password, foundUser.getPassword(), foundUser.getSalt())){
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.getSession().setAttribute(USER, foundUser.getLogin());
                request.getSession().setAttribute(ROLE,foundUser.getRole());
                request.getSession().setAttribute(ID,foundUser.getId());
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                response.sendRedirect(lastCommand);
            }else{
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                request.setAttribute(LOGIN_ERROR_MESSAGE, "Incorrect login or password.Try again");
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }

        }catch (ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }

    }
}