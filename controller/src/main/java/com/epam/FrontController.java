package com.epam;

import com.epam.command.AbstractCommand;
import com.epam.command.factory.CommandFactory;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        AbstractCommand command;
        CommandFactory client = new CommandFactory();
        command = client.defineCommand(req);
        page = command.execute(req);
       

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
// вызов страницы ответа на запрос
            dispatcher.forward(req, resp);

        } else {
// установка страницы c cообщением об ошибке
            page = ConfigurationManager.getProperty("path.page.index");
            req.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            resp.sendRedirect(req.getContextPath() + page);
        }




    }
}
