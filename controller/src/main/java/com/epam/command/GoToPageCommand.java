package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.command.util.ControllerConstants.*;

public class GoToPageCommand implements AbstractCommand{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        try {
            String goToPage = "/index.jsp".equals(request.getParameter(ADDRESS)) ? "/index.jsp" :
                    "/jsp/" + request.getParameter(ADDRESS);
//            String goToPage = "/index.jsp";

//            request.setAttribute("message", request.getParameter("message"));
           /* if (request.getParameter("message") == null) {
                request.getSession().setAttribute(LAST_COMMAND, defineCommand(request, true));
            }*/
            request.getRequestDispatcher(goToPage).forward(request, response);
        } catch (IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
