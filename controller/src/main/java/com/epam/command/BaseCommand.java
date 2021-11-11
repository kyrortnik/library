package com.epam.command;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseCommand implements AbstractCommand {

    public BaseCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
//        request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
       /* String page = ConfigurationManager.getProperty("path.page.login");
        return page;*/
    }
}
