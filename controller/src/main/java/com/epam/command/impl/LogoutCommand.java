package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogoutCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        LOG.info("Start in Logout command");

        try {
            request.getSession().invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }

    }
}
