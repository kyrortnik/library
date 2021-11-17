package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class BaseCommand implements Command {

    private static final Logger log = Logger.getLogger(BaseCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start on Base Command");
        try{
            String errorPage= "frontController?command=go_To_Page&address=error.jsp";
            request.getRequestDispatcher(errorPage).forward(request,response);
        }catch (ServletException | IOException e){
            throw new ControllerException(e);
        }


    }
}
