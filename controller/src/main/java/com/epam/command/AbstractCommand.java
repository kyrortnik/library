package com.epam.command;

import com.epam.exception.ControllerException;
import com.epam.validator.ControllerValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.epam.util.ControllerConstants.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
     * Defines incoming url (command) as a String. It's needed in order to perform language change from any application page
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

    /**
     * Sets page number for request with multiple entities
     *
     * @param request, request to get page number from
     * @return page number
     */
    protected Long getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (isNull(currentPageParam) || currentPageParam.isEmpty()) {
            currentPageParam = DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPageParam);
    }


    /**
     * Check if user is the Admin
     *
     * @param request, request to get role from
     * @throws ControllerException throws ControllerException
     */
    protected void isValidAdminUser(HttpServletRequest request) throws ControllerException {
        String role = (String) request.getSession().getAttribute(ROLE);
        boolean isValidAdmin = nonNull(role) && role.equalsIgnoreCase("admin");
        if (!isValidAdmin) {
            throw new ControllerException("Only Admin has permission for this action");
        }
    }

    /**
     * Check if user is a User
     *
     * @param request, request to get role from
     * @throws ControllerException throws ControllerException
     */
    protected void isValidUser(HttpServletRequest request) throws ControllerException {
        String role = (String) request.getSession().getAttribute(ROLE);
        boolean isValidUser = nonNull(role) && (role.equalsIgnoreCase("user") || role.equalsIgnoreCase("admin"));
        if (!isValidUser) {
            throw new ControllerException("Only Users and Admin van perform this action");
        }
    }

}
