package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;
import com.epam.security.Salt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class RegistrationCommand extends AbstractCommand implements Command {

    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService userService = factory.getUserService();

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in RegistrationCommand");
        String lastCommand;
        String login = request.getParameter(LOGIN);;
        String password = request.getParameter(PASSWORD);
        String secondPassword = request.getParameter(SECOND_PASSWORD);
        boolean passwordsEquals = password.equals(secondPassword);
        String saltBytes = Salt.generateSalt();
        User user = new User(login, Salt.generateEncryptedPassword(password,saltBytes),USER, saltBytes);

 try {
            if (passwordsEquals && userService.registration(user)){

            lastCommand = "frontController?command=go_To_Page&address=main.jsp";
            UserDTO userDTO = userService.find(user);
            request.getSession().setAttribute(USER, userDTO.getLogin());
            request.getSession().setAttribute(ROLE,userDTO.getRole());
            request.getSession().setAttribute(ID,userDTO.getId());
            response.sendRedirect(lastCommand);
            }
            else {
            lastCommand = "frontController?command=go_To_Page&address=login.jsp";
            request.setAttribute(REGISTRATION_ERROR_MESSAGE,"Registration failed. Check that login is not empty and two passwords match.");
            request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }
    }
}
