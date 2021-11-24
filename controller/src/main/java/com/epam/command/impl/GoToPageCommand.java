package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class GoToPageCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(GoToPageCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in GoToPageCommand");

        try {
            String goToPage = "/index.jsp".equals(request.getParameter(ADDRESS)) ? "/index.jsp" : "/WEB-INF/jsp/" + request.getParameter(ADDRESS);
            request.getRequestDispatcher(goToPage).forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
