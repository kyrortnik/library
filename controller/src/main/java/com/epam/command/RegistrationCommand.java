package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.MessageManager;
import entity.User;
import services.ServiceFactory;
import services.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements AbstractCommand{

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_SECOND_PASSWORD = "secondPassword";

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.createUserService();



    @Override
    public String execute(HttpServletRequest request) {

        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String pass2 = request.getParameter(PARAM_NAME_SECOND_PASSWORD);
        User user = new User(1,login,pass,"user");

        if (userService.registrate(user)){
            request.setAttribute("user",login);
            page = ConfigurationManager.getProperty("path.page.main");

        }else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        // second password needed for validation - two passwords should be equal


        return page;
    }
}
