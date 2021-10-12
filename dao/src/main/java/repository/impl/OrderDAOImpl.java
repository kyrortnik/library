package repository.impl;

import entity.Order;
import repository.ConnectionPool;
import repository.OrderDAO;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public OrderDAOImpl(ConnectionPool connectionPool) {

    }

    @Override
    public Order getEntity(Order element) {
        return null;
    }

    @Override
    public Order getEntityById(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean saveEntity(Order element) {
        return false;
    }

    @Override
    public boolean deleteEntity(Order element) {
        return false;
    }

    @Override
    public boolean updateEntity(Order element) {
        return false;
    }
}
