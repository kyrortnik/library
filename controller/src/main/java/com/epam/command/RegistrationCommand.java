package com.epam.command;


import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class RegistrationCommand implements Command {


    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService userService = factory.createUserService();
    private static final Logger log = Logger.getLogger(RegistrationCommand.class.getName());



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in RegistrationCommand");
        String lastCommand;
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        String pass2 = request.getParameter(SECOND_PASSWORD);
        User user = new User(login,pass,USER);


        try {
            if (userService.registration(user,pass2)){
            lastCommand = "frontController?command=go_To_Page&address=main.jsp";
            UserDTO userDTO = userService.find(user);
            request.getSession().setAttribute(USER, userDTO.getLogin());
            request.getSession().setAttribute(ROLE,userDTO.getRole());
            request.getSession().setAttribute(ID,userDTO.getId());
            response.sendRedirect(lastCommand);
            }
            else {
            lastCommand = "frontController?command=go_To_Page&address=login.jsp";
            request.setAttribute(MESSAGE,"Registration failed");
            request.getRequestDispatcher(lastCommand).forward(request,response);

            }
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }
    }
}
