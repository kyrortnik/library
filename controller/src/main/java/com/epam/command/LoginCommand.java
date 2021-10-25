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

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();



    public LoginCommand() { }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{
//        String page;
// извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        /*TODO create user with real id*/
        User user = new User(7,login,pass,"user");
// проверка логина и пароля
        try{
            if (userService.findUserByLogin(user)) {
                request.setAttribute("user", user.getLogin());
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.main")).forward(request,response);
// определение пути к main.jsp
//            page = ConfigurationManager.getProperty("path.page.main");

            } else {
                request.setAttribute("errorLoginPassMessage",
                        MessageManager.getProperty("message.loginerror"));
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request,response);
//            page = ConfigurationManager.getProperty("path.page.login");
            }
//        return page;
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }
}