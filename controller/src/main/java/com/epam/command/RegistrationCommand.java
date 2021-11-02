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

public class RegistrationCommand implements AbstractCommand{

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_SECOND_PASSWORD = "secondPassword";

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.createUserService();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

//        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
//        String role = String.valueOf(request.getSession().getAttribute("role"));
        String role = "user";
        String pass2 = request.getParameter(PARAM_NAME_SECOND_PASSWORD);
        User user = new User(login,pass,role);

        try {
            if (userService.registration(user,pass2)){
//                request.setAttribute("user",login);
                request.getSession().setAttribute("role",role);
               request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);

            }else {
                String message = "Didn't registrate. Try again";
                request.setAttribute("message",message);
                request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
            }
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

        // second password needed for validation - two passwords should be equal


//        return page;
    }
}
