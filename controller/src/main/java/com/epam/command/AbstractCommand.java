package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AbstractCommand {

    String execute(HttpServletRequest request, HttpServletResponse response);
}
