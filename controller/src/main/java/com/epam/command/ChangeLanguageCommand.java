package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {
    @Override


    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{
            request.getSession().setAttribute("local",request.getParameter("local"));
            String addressForRedirect = String.valueOf(request.getSession().getAttribute("lastCommand"));
            String path = addressForRedirect.equals("null") ? "/index.jsp" : addressForRedirect;
            response.sendRedirect(path);

        }catch (IOException e){
            throw new ControllerException(e);
        }


    }
}
