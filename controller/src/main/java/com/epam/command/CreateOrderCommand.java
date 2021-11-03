package com.epam.command;

import com.epam.OrderService;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;

import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateOrderCommand implements AbstractCommand {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private OrderService orderService = serviceFactory.createOrderService();
    private ReserveService reserveService = serviceFactory.createReserveService();




    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try {
           long userId = (Long) request.getSession().getAttribute("id");

           List<Reserve> reserveList = reserveService.getReservesForUser(userId);
           StringBuilder builder = new StringBuilder();
           for (Reserve reserve : reserveList){
              builder.append(reserve.getProductId()).append(" ");
           }
           String productIds = builder.toString().trim();

           Order order = new Order(productIds,userId);


            if (orderService.save(order)){
                if(reserveService.deleteReservesByUserId(userId)){
                    request.setAttribute("productAddedToOrder","Products ordered! Visit library to get them");
                }else{
                    request.setAttribute("errorNoCreateOrder","Products are not ordered!Unable to delete user reserves");
                }

            }else{
                request.setAttribute("errorNoCreateOrder","Products are not ordered!!");
            }
            request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
