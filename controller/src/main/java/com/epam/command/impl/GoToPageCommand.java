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
import static com.epam.util.ControllerConstants.LAST_COMMAND;

public class GoToPageCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(GoToPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try {
            if (needToAddLastCommand(request)) {
                String lastCommand = defineLastCommand(request, false);
                request.getSession().setAttribute(LAST_COMMAND, lastCommand);
            }
            LOG.info("Start in GoToPageCommand");
            String goToPage = "/index.jsp".equals(request.getParameter(ADDRESS)) ? "/index.jsp" : "/WEB-INF/jsp/" + request.getParameter(ADDRESS);
            request.getRequestDispatcher(goToPage).forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }

    private static boolean needToAddLastCommand(HttpServletRequest request) {
        boolean result = false;
        String lastCommand = (String) request.getSession().getAttribute(LAST_COMMAND);
        String pattern = ".*go_To_Page.*";
        String patternShow = ".*show.*";

        if (lastCommand.matches(pattern) || lastCommand.matches(patternShow)) {
            result = true;
        }
        return result;
    }
}
