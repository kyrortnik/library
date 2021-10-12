package services;

import impl.BookServiceImpl;
import impl.OrderServiceImpl;
import impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;

    private ServiceFactory() {
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.bookService = new BookServiceImpl();
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

    public static ServiceFactory getInstance(){
        return INSTANCE;
    }
}
