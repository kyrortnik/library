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
    SHOWUSERS {
        {this.command = new ShowUsersCommand();}
    },

    SHOWPRODUCTS {
        {this.command = new ShowProductsCommand();}
    },
    SHOW_ORDERS{
        {this.command = new ShowOrdersCommand();}
    },

    PRODUCTINFO{
        {this.command = new ProductInfoCommand();}
    },

    CREATEORDER{
        {this.command = new CreateOrderCommand();}
    },
    CREATERESERVE{
        {this.command = new CreateReserveCommand();}
    },
    SHOWRESERVES{
        {this.command = new ShowReservesCommand();}
    },
    ORDERINFO{
        {this.command = new OrderInfoCommand();}
    },
    GOTOPAGE{
        {this.command = new GoToPageCommand();}
    },
    CHANGELANGUAGE{
        {this.command = new ChangeLanguageCommand();}
    },
    DELETERESERVE{
        {this.command = new DeleteReserveCommand();}
    },
    DELETEPRODUCTFROMORDER{
        {this.command = new DeleteProductFromOrder();}
    },
    CREATEBOOK{
        {this.command = new CreateBookCommand();}
    },
    UPDATEBOOK{
        {this.command = new UpdateBookCommand();}
    },
    DELETEBOOK{
        {this.command = new DeleteBookCommand();}
    };
    public Command getCurrentCommand() {
        return command;
    }

    Command command;
}
