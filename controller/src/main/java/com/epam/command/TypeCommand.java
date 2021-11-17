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

    SHOW_PRODUCTS {
        {this.command = new ShowProductsCommand();}
    },

    PRODUCT_INFO{
        {this.command = new ProductInfoCommand();}
    },

    CREATE_ORDER{
        {this.command = new CreateOrderCommand();}
    },
    CREATE_RESERVE{
        {this.command = new CreateReserveCommand();}
    },
    SHOW_RESERVES{
        {this.command = new ShowReservesCommand();}
    },
    ORDER_INFO{
        {this.command = new OrderInfoCommand();}
    },
    GO_TO_PAGE{
        {this.command = new GoToPageCommand();}
    },
    CHANGE_LANGUAGE{
        {this.command = new ChangeLanguageCommand();}
    },
    DELETE_RESERVE{
        {this.command = new DeleteReserveCommand();}
    },
    DELETE_BOOK_FROM_ORDER{
        {this.command = new DeleteBookFromOrder();}
    },
    CREATE_BOOK{
        {this.command = new CreateBookCommand();}
    },
    UPDATE_BOOK{
        {this.command = new UpdateBookCommand();}
    },
    DELETE_BOOK{
        {this.command = new DeleteBookCommand();}
    };
    public Command getCurrentCommand() {
        return command;
    }

    Command command;
}
