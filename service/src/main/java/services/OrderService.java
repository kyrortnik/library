package services;

import entity.Order;

import java.util.List;

public interface OrderService {

    boolean create(Order order);

    boolean update(Order order);

    boolean delete(Order order);

    List<Order> getAll();




}
