package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class ChangeLanguageCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ChangeLanguageCommand.class.getName());

    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in ChangeLanguageCommand");
        String lastCommand = (String) request.getSession().getAttribute(LAST_COMMAND);
        controllerValidator.stringParameterValidation(lastCommand);
        try {
            request.getSession().setAttribute(LOCAL, request.getParameter(LOCAL));
            request.getSession().setAttribute(MESSAGE, null);
            String path = "null".equals(lastCommand) ? "/frontController?command=go_To_Page?address=error.jsp" : lastCommand;
            response.sendRedirect(path);
        } catch (IOException e) {
            throw new ControllerException(e);
        }


    }
}
