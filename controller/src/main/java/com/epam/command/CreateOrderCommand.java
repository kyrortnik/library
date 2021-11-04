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
import java.util.logging.Logger;

public class CreateOrderCommand implements AbstractCommand {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private OrderService orderService = serviceFactory.createOrderService();
    private ReserveService reserveService = serviceFactory.createReserveService();

    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());




    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateOrderCommand");

        try {
           long userId = (Long) request.getSession().getAttribute("id");
           List<Reserve> reserveList = reserveService.getReservesForUser(userId);
           StringBuilder builder = new StringBuilder();
           for (Reserve reserve : reserveList){
              builder.append(reserve.getProductId()).append(" ");
           }
           String productIds = builder.toString().trim();

           Order order = new Order(productIds,userId);
           String pageForRedirect = "frontController?command=goToPage&address=main.jsp";


            if (orderService.save(order)){
                if(reserveService.deleteReservesByUserId(userId)){
                    request.setAttribute("productAddedToOrder","Products ordered! Visit library to get them");
                }else{
                    request.setAttribute("errorNoCreateOrder","Products are not ordered!Unable to delete user reserves");
                }

            }else{

                if (orderService.update(order)){
                    reserveService.deleteReservesByUserId(userId);
                    request.setAttribute("productAddedToOrder","Products ordered! Visit library to get them");
                }else {
                    request.setAttribute("errorNoCreateOrder","Products are not ordered!Unable to delete user reserves");
                }
               // request.setAttribute("errorNoCreateOrder","Products are not ordered!!");
            }
//            request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);
            response.sendRedirect(pageForRedirect);

        } catch (ServiceException | IOException  e) {
            throw new ControllerException(e);
        }
    }
}
