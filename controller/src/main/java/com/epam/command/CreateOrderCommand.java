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

import static com.epam.command.util.ControllerConstants.*;
public class CreateOrderCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.createOrderService();
    private final ReserveService reserveService = serviceFactory.createReserveService();

    private static final Logger log = Logger.getLogger(CreateOrderCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateOrderCommand");
        try {

            long userId = (Long) request.getSession().getAttribute(ID);
            List<Reserve> reserveList = reserveService.getReservesForUser(userId);
            Order order = createOrder(userId, reserveList);
            String pageForRedirect = "frontController?command=go_To_Page&address=main.jsp";
            request.getSession().setAttribute(LAST_COMMAND,pageForRedirect);

            if (orderService.productsAlreadyOrdered(reserveList)){
                request.setAttribute(MESSAGE,"Some product/s on the list is already ordered!");
                request.getRequestDispatcher(pageForRedirect).forward(request, response);
            }else{
                if (orderService.create(order) && reserveService.deleteReservesByUserId(userId)){
                    request.getSession().setAttribute(MESSAGE, "Products ordered! Visit library to get them");
                    response.sendRedirect(pageForRedirect);
                }else{
                    request.setAttribute(MESSAGE,"Couldn't create or update existing order.");
                    request.getRequestDispatcher(pageForRedirect).forward(request,response);
                }
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
