package com.epam.command;

import com.epam.command.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AbstractCommand {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException;
}
