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

            UserDTO userDTO = userService.logination(user);
            if (userDTO != null){
                request.getSession().setAttribute("user", userDTO.getLogin());
                request.getSession().setAttribute("role",userDTO.getRole());
                request.getSession().setAttribute("id",userDTO.getId());
                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }else{
//                request.setAttribute("errorLoginPassMessage", "Error while logining");
//                request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
            }

        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }
}