package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements AbstractCommand{
    @Override


    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{
            request.getSession().setAttribute("local",request.getParameter("local"));
//            response.sendRedirect("/jsp/login.jsp");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);

        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }


    }
}
