package com.epam.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static com.epam.util.ControllerConstants.*;

public abstract class AbstractCommand implements Command {


    protected void successfulProcess(HttpServletRequest request, String lastCommand, String message) throws IOException {
        request.getSession().setAttribute(LAST_COMMAND,lastCommand);
        request.getSession().setAttribute(MESSAGE,message);
    }

    protected void unsuccessfulProcess(HttpServletRequest request, String lastCommand,String message) throws IOException, ServletException {
        request.getSession().setAttribute(LAST_COMMAND,lastCommand);
        request.getSession().setAttribute(MESSAGE,message);
    }


    protected String defineLastCommand(HttpServletRequest request, boolean withPage) {
        /*
        name and value as Map<String, String[]>
         */
        Map<String, String[]> params = request.getParameterMap();
        /*
        String to represent lastCommand
         */
        String lastCommand;
        /*
        frontController is only servlet
         */
        StringBuilder sb = new StringBuilder(FRONT_CONTROLLER);
        /*
        creating a line of [name=value&]
         */
        String page = "0";
        for (String k : params.keySet()) {
            sb.append(k).append("=");
            if (!k.equals(PAGE)) {
                sb.append(params.get(k)[0]).append("&");
            } else {
                page = params.get(k)[0];
                break;
            }
        }
        /*
        when withPage=true, and command has key("page"), then returns command with page number
         */
        if (!page.equals("0")) {
            if (withPage) {
                lastCommand = sb.append(page).toString();
            } else {
                lastCommand = sb.toString();
            }
        } else {
            lastCommand = sb.substring(0, sb.length() - 1);
        }
        return lastCommand;
    }
}
