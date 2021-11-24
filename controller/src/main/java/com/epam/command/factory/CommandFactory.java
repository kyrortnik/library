package com.epam.command.factory;


import com.epam.command.Command;
import com.epam.command.TypeCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.COMMAND;
import static com.epam.util.ControllerConstants.MESSAGE;
import static java.util.Objects.nonNull;


public class CommandFactory {
    private static final Logger LOG = Logger.getLogger(CommandFactory.class.getName());

    private static final CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public Command defineCommand(HttpServletRequest request) {
        LOG.info("Start in CommandFactory.defineCommand():");

        Command current = TypeCommand.BASE.getCurrentCommand();
        String command = request.getParameter(COMMAND);
        if (nonNull(command) && !command.isEmpty()) {
            try {
                TypeCommand typeCommand = TypeCommand.valueOf(command.toUpperCase());
                current = typeCommand.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                request.getSession().setAttribute(MESSAGE, "Error. Unknown command");
                LOG.info("Unknown command");
            }

        } else {
            LOG.info("Null or Empty Command. Redirect to BaseCommand");
        }
        return current;
    }
}
