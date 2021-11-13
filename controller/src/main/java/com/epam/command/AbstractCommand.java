package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class AbstractCommand implements Command{


    protected String defineCommand(HttpServletRequest request, boolean withPage) {
        /*
        name and value as Map<String, String[]>
         */
        Map<String, String[]> params = request.getParameterMap();
        /*
        FrontController is only servlet
         */
        StringBuilder sb = new StringBuilder("frontController?");
        /*
        creating a line of [name=value&]
         */
        String page = "0";
        for (String k : params.keySet()) {
            sb.append(k).append("=");
            if (!k.equals("page")) {
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
                return sb.append(page).toString();
            } else {
                return sb.toString();
            }
        } else {
            return sb.substring(0, sb.length() - 1);
        }
    }
}
