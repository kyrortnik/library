package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements AbstractCommand {
   // Receiver receiver;

    public LogoutCommand() {
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

//        String page = ConfigurationManager.getProperty("path.page.index");
        try{
            request.getSession().invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }catch (IOException | ServletException e ){
           throw new ControllerException(e);
        }


//        return page;
    }
}
