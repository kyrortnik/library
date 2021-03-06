package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.ADDRESS;

public class GoToPageCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(GoToPageCommand.class);

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
