package com.epam.impl;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.OrderDAO;
import com.epam.OrderService;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderServiceImpl implements OrderService {

    private static final OrderDAO orderDAO = DAOFactory.getInstance().createOrderDAO();


    @Override
    public boolean create(Order order) throws ServiceException {
        try{
            return orderDAO.save(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean update(Order order) throws ServiceException {
        try{
            return orderDAO.update(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Order order) throws ServiceException {
        try{
            return orderDAO.delete(order);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Order> getAll() throws ServiceException {
        try{
            return orderDAO.getAll();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Order getByUserId(Order order) throws ServiceException {
        try{
            Long userId= order.getUserId();
            return orderDAO.getByUserId(userId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public Order getByUserId(Long userId) throws ServiceException {
        try{
            return orderDAO.getByUserId(userId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

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
    public boolean productAlreadyOrdered(Reserve reserve) throws ServiceException {
        try{
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
            if (foundOrder == null){
                return false;
            }
            Matcher matcher = pattern.matcher(foundOrder.getProductIds());
            if (matcher.find()){
                productAlreadyOrdered = true;
            }
            return productAlreadyOrdered;
        }catch (ServiceException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean save(Order order) throws ServiceException {
        try{
            Long userId = order.getUserId();
            Order foundOrder = orderDAO.getByUserId(userId);
            if (foundOrder == null){
                return orderDAO.save(order);
            }else {
                return false;
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean productsAlreadyOrdered(List<Reserve> reserveList) throws ServiceException {
        boolean productAlreadyOrdered;
        try{
            for (Reserve reserve : reserveList) {
                productAlreadyOrdered = productAlreadyOrdered(reserve);
                if (productAlreadyOrdered) {
                    return true;
                }
            }
            return false;
        }catch (ServiceException e){
            throw new ServiceException(e);
        }


    }
}
