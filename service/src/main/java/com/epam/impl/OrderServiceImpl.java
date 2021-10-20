package com.epam.impl;

import com.epam.entity.Order;
import com.epam.repository.DAOFactory;
import com.epam.repository.OrderDAO;
import com.epam.OrderService;

import java.util.List;

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
}
