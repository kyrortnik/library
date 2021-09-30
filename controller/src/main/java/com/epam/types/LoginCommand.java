package com.epam.types;

import com.epam.ConfigurationManager;
import com.epam.validator.LoginLogic;
import com.epam.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements AbstractCommand {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";


    public LoginCommand() { }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
// извлечение из запроса логина и пароля
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
// проверка логина и пароля
        if (LoginLogic.checkLogin(login, pass)) {
            request.setAttribute("user", login);
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