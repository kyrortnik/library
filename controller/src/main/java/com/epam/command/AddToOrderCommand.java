package com.epam.command;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddToOrderCommand implements AbstractCommand{

    private OrderService orderService = ServiceFactory.getInstance().createOrderService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        //check whether this product is reserved
        //check if such order for this user exists
        //logic on order creation
        boolean isReserved = (Boolean) request.getAttribute("isReserved");
        if (!isReserved){

        }







    }
}
