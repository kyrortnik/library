package com.epam.impl;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.OrderDAO;
import com.epam.OrderService;

import java.rmi.ServerException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderServiceImpl implements OrderService {

    private static final OrderDAO orderDAO = DAOFactory.getInstance().createOrderDAO();


    @Override
    public boolean create(Order order) {
        return orderDAO.save(order);
    }

    @Override
    public boolean update(Order order) {
        return orderDAO.update(order);
    }

    @Override
    public boolean delete(Order order) {
        return orderDAO.delete(order);
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public Order getByUserId(Order order) {
        return orderDAO.getByUserId(order);
    }

    @Override
    public Order getByUserId(Long userId) {
        return orderDAO.getByUserId(userId);
    }


    @Override
    public boolean relationExists(Order order,String bookId) {

        boolean relationExists = false;
        String[] books = order.getProductIds().split(" ");
        for (String book : books) {
            if (book.equals(bookId)) {
                relationExists = true;
            }
        }
        return relationExists;
    }

    @Override
    public boolean productAlreadyOrdered(Reserve reserve) {
        boolean productAlreadyOrdered = false;
        long productId = reserve.getProductId();
        String regex = "(?<=\\s|^)" +productId + "(?=\\s|$)";
        Pattern pattern = Pattern.compile(regex);
//        List<Order> allOrders = getAll();
//        for (Order order: allOrders){
//            Matcher matcher = pattern.matcher(order.getProductIds());
//            if (matcher.find()){
//                productAlreadyOrdered = true;
//                break;
//            }
//        }
        Order foundOrder = getByUserId(reserve.getUserId());
        Matcher matcher = pattern.matcher(foundOrder.getProductIds());
        if (matcher.find()){
            productAlreadyOrdered = true;
        }
        return productAlreadyOrdered;
    }

    @Override
    public boolean save(Order order) throws ServiceException {
        try{
            Order foundOrder = orderDAO.getByUserId(order);
            if (Objects.isNull(foundOrder.getUserId())){
                return orderDAO.save(order);
            }else {
                return false;
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean productsAlreadyOrdered(List<Reserve> reserveList) {
        boolean productAlreadyOrdered;

        for (Reserve reserve : reserveList) {
            productAlreadyOrdered = productAlreadyOrdered(reserve);
            if (productAlreadyOrdered) {
                return true;
            }
        }
        return false;
    }
}
