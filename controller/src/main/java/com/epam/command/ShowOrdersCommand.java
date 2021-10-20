package com.epam.command;

import com.epam.ConfigurationManager;
import entity.Order;
import services.OrderService;
import services.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class ShowOrdersCommand implements AbstractCommand{

    private OrderService serviceFactory = ServiceFactory.getInstance().createOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        ArrayList<Order> orders =(ArrayList<Order>)serviceFactory.getAll();
        request.setAttribute("orders",orders);
        page = ConfigurationManager.getProperty("path.page.orders");
        return page;

    }
}
