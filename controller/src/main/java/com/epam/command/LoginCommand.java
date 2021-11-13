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


public class LoginCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.createUserService();

    private static final Logger log = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        User user = new User(login,pass,USER);
        String lastCommand;

        try{
            log.info("Start in LoginCommand");
            UserDTO userDTO = userService.logination(user);
            if (userDTO != null){
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.getSession().setAttribute(USER, userDTO.getLogin());
                request.getSession().setAttribute(ROLE,userDTO.getRole());
                request.getSession().setAttribute(ID,userDTO.getId());
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                response.sendRedirect(lastCommand);
            }else{
                lastCommand = "frontController?command=go_To_Page&address=login.jsp";
                request.setAttribute(MESSAGE, "Error while logging");
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }

        }catch (ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }

    }
}