package com.epam.repository;


import com.epam.repository.impl.*;


public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final ReserveDAO reserveDAO;

    private DAOFactory() {
        ConnectionPool connectionPool = new ConnectionPoolImpl(new PropertyInitializer());
        bookDAO = new BookDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
        orderDAO = new OrderDAOImpl(connectionPool);
        reserveDAO = new ReserveDAOImpl(connectionPool);

    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public ReserveDAO getReserveDAO() {
        return reserveDAO;
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }


}
