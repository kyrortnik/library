package com.epam.types;

public enum TypeCommand {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };
    AbstractCommand command;

    public AbstractCommand getCurrentCommand() {
        return command;
    }
}
