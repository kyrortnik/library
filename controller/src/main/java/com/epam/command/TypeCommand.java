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
    },
    SHOW_USERS {
        {this.command = new ShowUsersCommand();}
    },

    SHOW_PRODUCTS{
        {this.command = new ShowProductsCommand();}
    },
    SHOW_ORDERS{
        {this.command = new ShowOrdersCommand();}
    };

    public AbstractCommand getCurrentCommand() {
        return command;
    }

    AbstractCommand command;
}
