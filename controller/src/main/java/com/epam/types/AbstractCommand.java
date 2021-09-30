package com.epam.types;

import javax.servlet.http.HttpServletRequest;

public interface AbstractCommand {

    String execute(HttpServletRequest request);
}
