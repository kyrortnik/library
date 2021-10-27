package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.MessageManager;
import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginCommand implements AbstractCommand {



    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.createUserService();



    public LoginCommand() { }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        User user = new User(login,pass,"user");

        try{
            if (userService.findUserByLogin(user)) {
                request.getSession().setAttribute("user", user.getLogin());
                request.getSession().setAttribute("role",user.getRole());
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.main")).forward(request,response);

            } else {
                request.setAttribute("errorLoginPassMessage",
                        MessageManager.getProperty("message.loginerror"));
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request,response);

            }
//        return page;
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }
}