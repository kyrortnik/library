package com.epam.command;


import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;
import com.epam.OrderService;
import com.epam.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class ShowOrdersCommand implements AbstractCommand{

    private OrderService serviceFactory = ServiceFactory.getInstance().createOrderService();
    private static final Logger log = Logger.getLogger(ShowOrdersCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        log.info("Start in ShowOrdersCommand");

        ArrayList<Order> orders =(ArrayList<Order>)serviceFactory.getAll();
        try{
            request.setAttribute("orders",orders);
            response.sendRedirect("/jsp/orders.jsp");
        }catch (IOException e){
            throw new ControllerException(e);
        }

//        return page;

    }
}
