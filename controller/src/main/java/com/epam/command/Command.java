package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    /**
     * Method for Command execution
     *
     * @param request,  HttpServletRequest to be processed
     * @param response, HttpServletResponse to be processed
     * @throws ControllerException throws ControllerException
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException;

}
