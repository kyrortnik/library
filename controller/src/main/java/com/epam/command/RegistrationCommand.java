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

public class RegistrationCommand implements Command {

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
            UserDTO userDTO = userService.get(user);
            request.getSession().setAttribute("id",userDTO.getId());
            response.sendRedirect(addressForRedirect);
            }else {
                request.setAttribute("registrationFail","Registration failed");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);

            }

        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }
    }
}
