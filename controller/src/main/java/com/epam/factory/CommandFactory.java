package com.epam.factory;


import com.epam.command.Command;
import com.epam.command.CommandEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.util.ControllerConstants.COMMAND;
import static com.epam.util.ControllerConstants.MESSAGE;
import static java.util.Objects.nonNull;

public class CommandFactory {

    private static final Logger LOG = Logger.getLogger(CommandFactory.class);
    private static final CommandFactory INSTANCE = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Method to define command which comes from client
     *
     * @param request, HttpServletRequest to be processed
     * @return Command which will be executed in Front Controller
     */
    public Command defineCommand(HttpServletRequest request) {
        LOG.info("Start in CommandFactory.defineCommand():");

        Command current = CommandEnum.BASE.getCurrentCommand();
        String command = request.getParameter(COMMAND);
        if (nonNull(command) && !command.isEmpty()) {
            try {
                CommandEnum commandEnum = CommandEnum.valueOf(command.toUpperCase());
                current = commandEnum.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                request.getSession().setAttribute(MESSAGE, "Error. Unknown command");
                LOG.error("Unknown command");
            }

        } else {
            LOG.error("Null or Empty Command. Redirect to BaseCommand");
        }
        return current;
    }
}
