package com.epam.command.factory;

import com.epam.MessageManager;
import com.epam.command.AbstractCommand;
import com.epam.command.BaseCommand;
import com.epam.command.TypeCommand;

import javax.servlet.http.HttpServletRequest;


public class CommandFactory {

    public AbstractCommand defineCommand(HttpServletRequest request){

        AbstractCommand current = new BaseCommand();
        String action = request.getParameter("command");
        if (action.isEmpty()){
            return current;
        }else {
            try {
                TypeCommand typeCommand = TypeCommand.valueOf(action.toUpperCase());
                current = typeCommand.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                request.setAttribute("wrongAction", action
                        + MessageManager.getProperty("message.wrongaction"));
            }
        }
        return current;
    }
}
