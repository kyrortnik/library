package com.epam.command;


import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RegistrationCommand implements AbstractCommand{

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_SECOND_PASSWORD = "secondPassword";

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.createUserService();
    private static final Logger log = Logger.getLogger(RegistrationCommand.class.getName());



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in RegistrationCommand");
        String addressForRedirect;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String role = "user";
        String pass2 = request.getParameter(PARAM_NAME_SECOND_PASSWORD);
        User user = new User(login,pass,role);

        try {
            if (userService.registration(user,pass2)){
            request.getSession().setAttribute("role",role);
            addressForRedirect = "frontController?command=goToPage&address=main.jsp";
            user = userService.get(user);
            request.getSession().setAttribute("id",user.getId());
            response.sendRedirect(addressForRedirect);
            }else {
                request.setAttribute("registrationFail","Registration failed");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);

            }

        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }
    }
}
