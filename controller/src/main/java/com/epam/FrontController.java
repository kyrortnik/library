package com.epam;

import com.epam.command.AbstractCommand;
import com.epam.command.exception.ControllerException;
import com.epam.command.factory.CommandFactory;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
       processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)  {
        AbstractCommand command;
        CommandFactory commandFactory = new CommandFactory();
        command = commandFactory.defineCommand(request);
        try{
             command.execute(request,response);
            }
        catch (ControllerException e){
            e.printStackTrace();
        }

       







    }
}
