package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class ChangeLanguageCommand implements Command {

    private static final Logger log = Logger.getLogger(ChangeLanguageCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in ChangeLanguageCommand");
        try{
            request.getSession().setAttribute(LOCAL,request.getParameter(LOCAL));
            request.getSession().setAttribute(MESSAGE,null);
            String addressForRedirect = String.valueOf(request.getSession().getAttribute(LAST_COMMAND));
            String path = addressForRedirect.equals("null") ? "/login.jsp" : addressForRedirect;

            response.sendRedirect(path);
        }catch (IOException e){
            throw new ControllerException(e);
        }


    }
}
