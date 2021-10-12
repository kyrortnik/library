package com.epam.command;

public enum TypeCommand {
    LOGIN {
        { this.command = new LoginCommand(); }
    },
    LOGOUT {
        { this.command = new LogoutCommand(); }
    },

    REGISTRATION {
        {this.command = new RegistrationCommand();}
    };
    AbstractCommand command;

    public AbstractCommand getCurrentCommand() {
        return command;
    }
}
