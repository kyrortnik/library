package com.epam;

import com.epam.impl.BookServiceImpl;
import com.epam.impl.OrderServiceImpl;
import com.epam.impl.ReserveServiceImpl;
import com.epam.impl.UserServiceImpl;
import com.epam.repository.DAOFactory;
import com.epam.validator.ServiceValidator;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;
    private final ReserveService reserveService;

    private ServiceFactory() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        ServiceValidator serviceValidator = ServiceValidator.getInstance();
        this.userService = new UserServiceImpl(daoFactory.getUserDAO(), serviceValidator);
        this.orderService = new OrderServiceImpl(daoFactory.getOrderDAO(), serviceValidator);
        this.bookService = new BookServiceImpl(daoFactory.getBookDAO(), serviceValidator);
        this.reserveService = new ReserveServiceImpl(daoFactory.getReserveDAO(), serviceValidator);
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public ReserveService getReserveService() {
        return reserveService;
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
}
