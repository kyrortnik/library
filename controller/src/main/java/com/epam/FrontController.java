package com.epam;

import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.command.factory.CommandFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command;
        command = CommandFactory.getInstance().defineCommand(request);
        try {
            command.execute(request, response);
        } catch (ControllerException e) {
            LOG.error(e.getMessage());
            request.getRequestDispatcher("frontController?command=goToPage&address=error.jsp").forward(request, response);
        }


    }
}
