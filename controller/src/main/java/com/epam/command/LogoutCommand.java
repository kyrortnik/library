package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class LogoutCommand implements AbstractCommand {
   // Receiver receiver;
   private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

//        String page = ConfigurationManager.getProperty("path.page.index");
        try{
            log.info("Start in Logout command");
            request.getSession().invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }catch (IOException | ServletException e ){
           throw new ControllerException(e);
        }


//        return page;
    }
}
