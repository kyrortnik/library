package com.epam.command;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;
import com.epam.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateOrderCommand implements AbstractCommand {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private OrderService orderService = serviceFactory.createOrderService();




    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try {
        //    long userId = (Long) request.getSession().getAttribute("id");
            Long userId = 1L;
//            long productID = (Long) request.getAttribute("id");
            long productID = 2L;
            Order order = new Order(userId,Long.toString(productID),userId);
            if (orderService.save(order)){
                request.setAttribute("productAddedToOrder","Product successfully added to Order list");
                request.getRequestDispatcher("/jsp/productInfo.jsp");
            }else{
                request.setAttribute("errorNoCreateOrder","Order is not added to Order list!");
            }

        } catch (ServiceException | IOException e) {
            throw new ControllerException(e);
        }
    }
}
