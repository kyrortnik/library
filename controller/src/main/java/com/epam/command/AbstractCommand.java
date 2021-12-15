package com.epam.command;

import com.epam.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.epam.util.ControllerConstants.*;

public abstract class AbstractCommand implements Command {

    protected static final ControllerValidator controllerValidator = ControllerValidator.getInstance();

    /**
     * @param request,     which comes from web
     * @param lastCommand, url of last performed command
     * @param message,     message to show on web page
     */
    protected void processRequest(HttpServletRequest request, String lastCommand, String message) {
        request.getSession().setAttribute(LAST_COMMAND, lastCommand);
        request.getSession().setAttribute(MESSAGE, message);
    }

    /**
     * Defines incoming url (command) as a String. Needed in order to perform language change from any application page
     *
     * @param request,  which comes from web
     * @param withPage, required when the command is pageable
     * @return url as a String, with all parameters
     */
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
