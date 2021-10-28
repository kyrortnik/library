package com.epam.command;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderInfoCommand implements AbstractCommand{


    private OrderService orderService = ServiceFactory.getInstance().createOrderService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{
            Long userId = (Long)request.getSession().getAttribute("id");
            Order order = orderService.getByUserId(userId);

            if (userId.equals(order.getUserId())){
                request.setAttribute("order",order);
            }else {
                request.setAttribute("noOrderCreated","No order created for you yet.");
            }
            request.getRequestDispatcher("/jsp/orderInfo.jsp").forward(request,response);
        }catch (IOException | ServletException e ){
            throw new ControllerException(e);
        }



    }
}
