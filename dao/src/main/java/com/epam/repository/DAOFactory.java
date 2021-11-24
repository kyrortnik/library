package com.epam.repository;


import com.epam.repository.impl.*;


public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();

    private final ConnectionPool connectionPool = new ConnectionPoolImpl(new PropertyInitializer());

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final ReserveDAO reserveDAO;

    private DAOFactory(){
        bookDAO = new BookDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
        orderDAO = new OrderDAOImpl(connectionPool);
        reserveDAO = new ReserveDAOImpl(connectionPool);

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
