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

public class CreateOrderCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private OrderService orderService = serviceFactory.createOrderService();
    private ReserveService reserveService = serviceFactory.createReserveService();

    private static final Logger log = Logger.getLogger(CreateOrderCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateOrderCommand");

        try {

            long userId = (Long) request.getSession().getAttribute("id");
            List<Reserve> reserveList = reserveService.getReservesForUser(userId);
            String lastCommand = "frontController?command=goToPage&address=main.jsp";


            /**TODO check that order for user doesn't exist*/
                if (orderService.productsAlreadyOrdered(reserveList)){
                    request.setAttribute("message","Some product/s on the list is already ordered!");
                    request.getSession().setAttribute("lastCommand",lastCommand);
                    request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
                }else{
                    Order order = createOrder(userId, reserveList);
                    if (orderService.save(order) && reserveService.deleteReservesByUserId(userId)){
                        request.setAttribute("productAddedToOrder", "Products ordered! Visit library to get them");
                    }else{
                        orderService.update(order);
                        reserveService.deleteReservesByUserId(userId);
//                        request.setAttribute("productAddedToOrder", "Products ordered! Visit library to get them");
                        request.setAttribute("productAddedToOrder","Order is updated");
                    }
                    request.getSession().setAttribute("lastCommand",lastCommand);
                    response.sendRedirect(lastCommand);
                }


        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }

    private Order createOrder(long userId, List<Reserve> reserveList) {
        StringBuilder builder = new StringBuilder();
        for (Reserve reserve : reserveList) {
            builder.append(reserve.getProductId()).append(" ");
        }
        String productIds = builder.toString().trim();

        return new Order(productIds, userId);
    }

}
