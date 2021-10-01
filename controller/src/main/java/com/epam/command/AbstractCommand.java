package com.epam.command;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {

    String execute(HttpServletRequest request);
}
