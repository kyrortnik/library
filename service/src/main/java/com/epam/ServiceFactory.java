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
        this.userService = new UserServiceImpl(daoFactory.getUserDAO());
        this.orderService = new OrderServiceImpl(daoFactory.getOrderDAO());
        this.bookService = new BookServiceImpl(daoFactory.getBookDAO());
        this.reserveService = new ReserveServiceImpl(daoFactory.getReserveDAO());
    }

    public UserService getUserService(){ return  userService; }

    public OrderService getOrderService(){
        return orderService;
    }

    public BookService getBookService(){
        return bookService;
    }

    public ReserveService getReserveService(){return reserveService;}

    public static ServiceFactory getInstance(){
        return INSTANCE;
    }
}
