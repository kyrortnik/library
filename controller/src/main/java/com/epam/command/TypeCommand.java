package com.epam.command;

import com.epam.command.impl.*;


public enum TypeCommand {

    BASE(new BaseCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    SHOW_PRODUCTS(new ShowProductsCommand()),
    BOOK_INFO(new BookInfoCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    CREATE_RESERVE(new CreateReserveCommand()),
    SHOW_RESERVES(new ShowReservesCommand()),
    ORDER_INFO(new OrderInfoCommand()),
    GO_TO_PAGE(new GoToPageCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    DELETE_RESERVE(new DeleteReserveCommand()),
    DELETE_BOOK_FROM_ORDER(new DeleteBookFromOrderCommand()),
    CREATE_BOOK(new CreateBookCommand()),
    UPDATE_BOOK(new UpdateBookCommand()),
    DELETE_BOOK(new DeleteBookCommand());

    public Command getCurrentCommand() {
        return command;
    }

    Command command;

    TypeCommand(Command command) {
        this.command = command;
    }
}
