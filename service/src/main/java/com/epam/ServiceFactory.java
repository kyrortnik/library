package com.epam;

import com.epam.impl.BookServiceImpl;
import com.epam.impl.OrderServiceImpl;
import com.epam.impl.ReserveServiceImpl;
import com.epam.impl.UserServiceImpl;
import com.epam.repository.DAOFactory;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;
    private final ReserveService reserveService;

    private ServiceFactory() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.userService = new UserServiceImpl(daoFactory.createUserDAO());
        this.orderService = new OrderServiceImpl(daoFactory.createOrderDAO());
        this.bookService = new BookServiceImpl(daoFactory.createBookDAO());
        this.reserveService = new ReserveServiceImpl(daoFactory.createReserveDAO());
    }

    public UserService createUserService(){ return  userService; }

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
