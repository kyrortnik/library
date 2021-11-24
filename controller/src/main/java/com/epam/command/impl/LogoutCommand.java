package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class LogoutCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try {
            LOG.info("Start in Logout command");
            request.getSession().invalidate();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }

    }
}
