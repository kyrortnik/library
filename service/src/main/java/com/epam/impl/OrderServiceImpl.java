package com.epam.impl;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.OrderDAO;
import com.epam.OrderService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.validator.ServiceValidator.validation;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    private static final Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public boolean create(Order order) throws ServiceException {
        if (!validation(order)){
            return false;
        }try{
            Long userId = order.getUserId();
            Order foundOrder = orderDAO.getByUserId(userId);
            if (foundOrder == null){
                return orderDAO.save(order);
            }else {
                return update(order);
            }
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Order order) throws ServiceException {
        if (!validation(order)){
            return false;
        }try{
            Long userId = order.getUserId();
            Order foundOrder = orderDAO.getByUserId(userId);
            if (foundOrder != null){
                return orderDAO.update(order);
            }else {
                return false;
            }
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Order order) throws ServiceException {
        if (!validation(order)){
            return false;
        }else{
            try{
                return orderDAO.delete(order);
            }catch (DAOException e){
                log.log(Level.SEVERE,"Exception: " + e);
                throw new ServiceException(e);
            }
        }


    }

//    @Override
//    public boolean deleteFromOrder(Order order) throws ServiceException {
//        try{
//            Order foundOrder = orderDAO.find(order);
//            String updatedProducts = getUpdatedProducts(order, foundOrder);
//            if (orderDAO.deleteFromOrder(order,updatedProducts)){
//                if (orderDAO.find(foundOrder).getProductIds().equals("")){
//                    return orderDAO.delete(foundOrder);
//                }
//                return  true;
//            }else{
//                return false;
//            }
//        }catch (Exception e){
//            log.log(Level.SEVERE,"Exception: " + e);
//            throw new ServiceException(e);
//        }
//    }
    @Override
    public boolean deleteFromOrder(Order order) throws ServiceException {
        try{
            Order existingOrder = orderDAO.find(order);
            String updatedProducts = getUpdatedProducts(order, existingOrder);
            if (orderDAO.deleteFromOrder(order,updatedProducts)){
                if (orderDAO.find(existingOrder).getProductIds().equals("")){
                    return orderDAO.delete(existingOrder);
                }
                return  true;
            }else{
                return false;
            }
        }catch (Exception e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public Order getByUserId(Long userId) throws ServiceException {
        if (!validation(userId)){
            return null;
        }try{
            return orderDAO.getByUserId(userId);
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public boolean productAlreadyOrdered(Reserve reserve) throws ServiceException {
        if (!validation(reserve)){
            return false;
        }try{
            boolean productAlreadyOrdered = false;
            long productId = reserve.getProductId();
            String regex = "(?<=\\s|^)" +productId + "(?=\\s|$)";
            Pattern pattern = Pattern.compile(regex);
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
            log.log(Level.SEVERE,"Exception: " + e);
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
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }

    private String getUpdatedProducts(Order order, Order foundOrder) {
        StringBuilder updatedProducts = new StringBuilder();
        String productToRemove = order.getProductIds();
        String[] oldProducts = foundOrder.getProductIds().split(" ");
        for (String oldProduct : oldProducts) {
            if (!oldProduct.equals(productToRemove)) {
                updatedProducts.append(oldProduct).append(" ");
            }
        }
        return updatedProducts.toString().trim();
    }


  /*  @Override
    public List<Order> getAll() throws ServiceException {
        try{
            return orderDAO.getAll();
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }*/



/*    @Override
    public Order getByUserId(Order order) throws ServiceException {
        if (!validation(order)){
            return null;
        }try{
            Long userId = order.getUserId();
            return orderDAO.getByUserId(userId);
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }*/


    /*@Override
    public boolean relationExists(Order order,String bookId) {
        if (!validation(order)){
            return false;
        }
        boolean relationExists = false;
        String[] books = order.getProductIds().split(" ");
        for (String book : books) {
            if (book.equals(bookId)) {
                relationExists = true;
                break;
            }
        }
        return relationExists;
    }*/

}
