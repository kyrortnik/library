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

    SHOWPRODUCTS {
        {this.command = new ShowProductsCommand();}
    },
    SHOW_ORDERS{
        {this.command = new ShowOrdersCommand();}
    },

    PRODUCTINFO{
        {this.command = new ProductInfoCommand();}
    },
//    ADDTOORDER{
//        {this.command = new AddToOrderCommand();}
//    },
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
    };
    public Command getCurrentCommand() {
        return command;
    }

    Command command;
}
