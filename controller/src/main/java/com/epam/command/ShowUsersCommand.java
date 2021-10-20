package com.epam.command;

import com.epam.ConfigurationManager;
import entity.User;
import services.ServiceFactory;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ShowUsersCommand implements AbstractCommand{

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String page;
            ArrayList<User> users = (ArrayList<User>)userService.getUsers();
            request.setAttribute("users",users);
            page = ConfigurationManager.getProperty("path.page.users");
            return page;

    }
}
