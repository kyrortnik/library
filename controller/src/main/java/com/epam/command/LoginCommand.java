package com.epam.command;

import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.entity.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class LoginCommand implements AbstractCommand {

    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());



    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.createUserService();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        User user = new User(login,pass,"user");
        String addressForRedirect;

        try{
            log.info("Start in LoginCommand");
            UserDTO userDTO = userService.logination(user);
            if (userDTO != null){
                request.getSession().setAttribute("user", userDTO.getLogin());
                request.getSession().setAttribute("role",userDTO.getRole());
                request.getSession().setAttribute("id",userDTO.getId());
                addressForRedirect = "frontController?command=goToPage&address=main.jsp";
//                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }else{
//                request.setAttribute("errorLoginPassMessage", "Error while logining");
//                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
                addressForRedirect = "frontController?command=goToPage&address=login.jsp&message=noSuchUser";
            }
            response.sendRedirect(addressForRedirect);

        }catch (IOException e){
            throw new ControllerException(e);
        }

    }
}