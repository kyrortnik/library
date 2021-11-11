package com.epam.command.factory;


import com.epam.command.Command;
import com.epam.command.BaseCommand;
import com.epam.command.TypeCommand;

import javax.servlet.http.HttpServletRequest;


public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory(){
    }

    public Command defineCommand(HttpServletRequest request){

        Command current = new BaseCommand();
        String command = request.getParameter("command");
        if (command.isEmpty()){
            return current;
        }else {
            try {
                TypeCommand typeCommand = TypeCommand.valueOf(command.toUpperCase());
                current = typeCommand.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                request.setAttribute("wrongAction", "Wrong Action");
            }
        }
        return current;
    }

    public static CommandFactory getInstance(){
        return INSTANCE;
    }
}
