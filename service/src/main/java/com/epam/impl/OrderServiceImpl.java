package com.epam.impl;

import com.epam.OrderService;
import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.OrderDAO;
import com.epam.validator.ServiceValidator;

import java.util.List;


public class OrderServiceImpl implements OrderService {


    private final OrderDAO orderDAO;
    private final ServiceValidator serviceValidator;

    public OrderServiceImpl(OrderDAO orderDAO, ServiceValidator serviceValidator) {
        this.serviceValidator = serviceValidator;
        this.orderDAO = orderDAO;
    }


    @Override
    public boolean create(Long userId, List<Long> bookIds) throws ServiceException {
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
    public boolean delete(Long id) throws ServiceException {
        serviceValidator.validation(id);
        try {
            return orderDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteFromOrder(Long userId, Long bookId) throws ServiceException {
        try {
            return orderDAO.deleteFromOrder(userId, bookId);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


}
