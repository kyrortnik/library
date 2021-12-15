package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.exception.ControllerException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.nonNull;

public class ChangeLanguageCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in ChangeLanguageCommand");

        String lastCommand = (String) request.getSession().getAttribute(LAST_COMMAND);

        try {
            isValidUser(request);
            request.getSession().setAttribute(LOCAL, request.getParameter(LOCAL));
            request.getSession().setAttribute(MESSAGE, null);
            String path = nonNull(lastCommand) ? lastCommand : "/frontController?command=go_To_Page&address=login.jsp";
            response.sendRedirect(path);
        } catch (IOException e) {
            throw new ControllerException(e);
        }


    }
}
