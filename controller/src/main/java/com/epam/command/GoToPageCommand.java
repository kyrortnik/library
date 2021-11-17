package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class GoToPageCommand implements Command {

    private static final Logger log = Logger.getLogger(GoToPageCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in GoToPageCommand");

        try {
            String goToPage = "/index.jsp".equals(request.getParameter(ADDRESS)) ? "/index.jsp" : "/jsp/" + request.getParameter(ADDRESS);
            request.getSession().setAttribute(MESSAGE,null);
            request.getRequestDispatcher(goToPage).forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
