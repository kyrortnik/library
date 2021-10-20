package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.entity.Order;
import com.epam.OrderService;
import com.epam.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


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
