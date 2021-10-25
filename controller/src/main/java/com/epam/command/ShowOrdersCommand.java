package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;
import com.epam.OrderService;
import com.epam.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class ShowOrdersCommand implements AbstractCommand{

    private OrderService serviceFactory = ServiceFactory.getInstance().createOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{
//        String page;
        ArrayList<Order> orders =(ArrayList<Order>)serviceFactory.getAll();
        try{
            request.setAttribute("orders",orders);
            response.sendRedirect(ConfigurationManager.getProperty("path.page.orders"));
        }catch (IOException e){
            throw new ControllerException(e);
        }

//        return page;

    }
}
