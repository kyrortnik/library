package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.validator.LoginLogic;
import com.epam.MessageManager;
import entity.User;
import services.ServiceFactory;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginCommand implements AbstractCommand {



    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();



    public LoginCommand() { }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
// извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        User user = new User(5,login,pass,"user");
// проверка логина и пароля
        if (/*userValidation*/ true) {
            request.setAttribute("user", user.getLogin());
// определение пути к main.jsp
            page = ConfigurationManager.getProperty("path.page.main");

        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}