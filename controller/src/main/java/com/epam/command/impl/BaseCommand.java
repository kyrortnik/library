package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(BaseCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in Base Command");
        try {
            isValidUser(request);
            String errorPage = "frontController?command=go_To_Page&address=error.jsp";
            request.getRequestDispatcher(errorPage).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ControllerException(e);
        }
    }
}
