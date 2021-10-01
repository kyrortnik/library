package com.epam.command;

import com.epam.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements AbstractCommand {
   // Receiver receiver;

    public LogoutCommand() {
    }


    @Override
    public String execute(HttpServletRequest request) {

        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();

        return page;
    }
}
