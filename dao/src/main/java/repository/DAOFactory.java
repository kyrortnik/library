package repository;


import repository.impl.BookDAOImpl;
import repository.impl.ConnectionPoolImpl;
import repository.impl.OrderDAOImpl;
import repository.impl.UserDAOImpl;


public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();
    private static final PropertyInitializer propertyInitializer = new PropertyInitializer();
    private static final ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;

    private DAOFactory(){
        bookDAO = new BookDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
        orderDAO = new OrderDAOImpl(connectionPool);

    }

    public BookDAO createBookDAO(){
        return  bookDAO;
    }

    public UserDAO createUserDAO(){
        return userDAO;
    }

    public OrderDAO createOrderDAO(){
        return orderDAO;
    }

    public static DAOFactory getInstance(){
        return INSTANCE;
    }



}
