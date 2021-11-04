package com.epam.command;

import com.epam.command.exception.ControllerException;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ShowUsersCommand implements AbstractCommand{

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        log.info("Start in ShowUsersCommand");


//        String page;
            ArrayList<User> users = (ArrayList<User>)userService.getUsers();
            try{
                request.setAttribute("users",users);
                response.sendRedirect("/jsp/users.jsp");
            }catch (IOException e){
                throw new ControllerException(e);
            }

//            return page;

    }
}
