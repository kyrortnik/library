package com.epam;

import com.epam.impl.BookServiceImpl;
import com.epam.impl.OrderServiceImpl;
import com.epam.impl.ReserveServiceImpl;
import com.epam.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;
    private final ReserveService reserveService;

    private ServiceFactory() {
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.bookService = new BookServiceImpl();
        this.reserveService = new ReserveServiceImpl();
    }

    public UserService createUserService(){
        return  userService;
    }

    public OrderService createOrderService(){
        return orderService;
    }

    public BookService createBookService(){
        return bookService;
    }

    public ReserveService createReserveService(){return reserveService;}

    public static ServiceFactory getInstance(){
        return INSTANCE;
    }
}
