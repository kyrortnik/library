package com.epam.command;

import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;
import com.epam.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class AddToOrderCommand implements AbstractCommand{

    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());

    private OrderService orderService = ServiceFactory.getInstance().createOrderService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in AddToOrderCommand");

        Long userId = (Long)request.getSession().getAttribute("id");
        String bookId = request.getParameter("bookId");
        String pageForRedirect = "frontController?command=goToPage&address=main.jsp";

        try {
            Order foundOrder = orderService.getByUserId(userId);
//            check on whether such order already exists
            if (foundOrder.getUserId().equals(userId)){
//                check that such product is not added to existing order
                if(!orderService.relationExists(foundOrder,bookId)){
                    String updatedProducts = foundOrder.getProductIds() + " " + bookId;
                    foundOrder.setProductIds(updatedProducts);
                    orderService.update(foundOrder);
                    request.setAttribute("message", "Order is updated");
                    pageForRedirect = "frontController?command=goToPage&address=main.jsp";
            }else{
                    pageForRedirect = "frontController?command=goToPage&address=main.jsp";
                }
                /*else{
                    request.setAttribute("message", "Order is not created");
                    pageForRedirect = "frontController?command=goToPage&address=productInfo.jsp";
                }*/
            }else{
                Order newOrder = new Order(bookId,userId);
                if (orderService.save(newOrder)){
                    request.setAttribute("message", "Order is created");
                    pageForRedirect = "frontController?command=goToPage&address=main.jsp";
                }else{
                    request.setAttribute("message", "Order is not created");
                    pageForRedirect = "frontController?command=goToPage&address=productInfo.jsp";
                }
            }
            response.sendRedirect(pageForRedirect);
        }catch (ServiceException | IOException e){
            throw new ControllerException(e);
        }

    }
}
