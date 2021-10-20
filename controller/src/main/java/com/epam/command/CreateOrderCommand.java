package com.epam.command;

import entity.Order;
import services.OrderService;
import services.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateOrderCommand implements AbstractCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

   /* private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private OrderService orderService = serviceFactory.createOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)  {

        try {
            long userId = (Long) request.getSession().getAttribute("id");
            long productID = (Long) request.getSession().getAttribute()
            Order order = new Order(userId, request.getParameter("userAddress"), request.getParameter("userPhone"));
            boolean orderCreated = orderService.saveNewOrder(order, reservedProductIds);

        } catch (ServiceException  | IOException e) {
            throw new ControllerException(e);
        }
    }*/
}
