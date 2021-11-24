package com.epam.impl;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.OrderDAO;
import com.epam.OrderService;
import com.epam.validator.ServiceValidator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OrderServiceImpl implements OrderService {


    private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class.getName());


    private final OrderDAO orderDAO;
    private final ServiceValidator serviceValidator = new ServiceValidator();

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public boolean create(Order order) throws ServiceException {
        serviceValidator.validation(order);
        boolean result;
        try {
            if (orderDAO.save(order)) {
                result = true;
            } else {
                result = update(order);
            }
            return result;
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createByUserId(Long userId, List<Long> bookIds) throws ServiceException {
        serviceValidator.validation(userId);
        serviceValidator.validation(bookIds);
        boolean result;
        try {
            Order order = new Order(bookIds, userId);
            if (orderDAO.save(order)) {
                result = true;
            } else {
                result = orderDAO.update(order);
            }
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Order order) throws ServiceException {
        serviceValidator.validation(order);
        try {
            Long userId = order.getUserId();
            Order foundOrder = orderDAO.getByUserId(userId);
            if (foundOrder != null) {
                return orderDAO.update(order);
            } else {
                return false;
            }
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        serviceValidator.validation(id);
        try {
            return orderDAO.delete(id);
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }

 /*   @Override
    public boolean deleteFromOrder(Long userId,Long bookId) throws ServiceException {
        try {
            Order existingOrder = orderDAO.find(order);
            String updatedProducts = getUpdatedProducts(order, existingOrder);
            if (orderDAO.deleteFromOrder(order, updatedProducts)) {
                if (orderDAO.find(existingOrder).getProductIds().equals("")) {
                    return orderDAO.delete(existingOrder);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }*/


    @Override
    public boolean deleteFromOrder(Long userId, Long bookId) throws ServiceException {
        try {

            return orderDAO.deleteFromOrder(userId, bookId);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public Order getByUserId(Long userId) throws ServiceException {
        serviceValidator.validation(userId);
        try {
            return orderDAO.getByUserId(userId);
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public boolean productAlreadyOrdered(Reserve reserve) throws ServiceException {
     /*   serviceValidator.validation(reserve);
        try {
            boolean productAlreadyOrdered = false;
            long productId = reserve.getProductId();
            String regex = "(?<=\\s|^)" + productId + "(?=\\s|$)";
            Pattern pattern = Pattern.compile(regex);
            Order foundOrder = getByUserId(reserve.getUserId());
            if (foundOrder == null) {
                return false;
            }
            Matcher matcher = pattern.matcher(foundOrder.getProductIds());
            if (matcher.find()) {
                productAlreadyOrdered = true;
            }
            return productAlreadyOrdered;
        } catch (ServiceException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }*/
        return false;
    }

    @Override
    public boolean productsAlreadyOrdered(List<Reserve> reserveList) throws ServiceException {
        boolean productAlreadyOrdered;
        try {
            for (Reserve reserve : reserveList) {
                productAlreadyOrdered = productAlreadyOrdered(reserve);
                if (productAlreadyOrdered) {
                    return true;
                }
            }
            return false;
        } catch (ServiceException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }

    private String getUpdatedProducts(Order order, Order foundOrder) {
        StringBuilder updatedProducts = new StringBuilder();
      /*  String productToRemove = order.getProductIds();
        String[] oldProducts = foundOrder.getProductIds().split(" ");
        for (String oldProduct : oldProducts) {
            if (!oldProduct.equals(productToRemove)) {
                updatedProducts.append(oldProduct).append(" ");
            }
        }*/
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
