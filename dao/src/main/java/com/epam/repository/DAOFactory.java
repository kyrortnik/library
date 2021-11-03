package com.epam.repository;


import com.epam.repository.impl.*;


public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();
    private static final PropertyInitializer propertyInitializer = new PropertyInitializer();
//    private static final ConnectionPool connectionPool = new ConnectionPoolImpl(propertyInitializer);

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final ReserveDAO reserveDAO;

    private DAOFactory(){
        bookDAO = new BookDAOImpl();
        userDAO = new UserDAOImpl();
        orderDAO = new OrderDAOImpl();
        reserveDAO = new ReserveDAOImpl();

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

    public ReserveDAO createReserveDAO(){ return reserveDAO;}

    public static DAOFactory getInstance(){
        return INSTANCE;
    }



}
