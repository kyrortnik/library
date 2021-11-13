package com.epam.command;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class DeleteProductFromOrder implements Command{

    private final OrderService orderService = ServiceFactory.getInstance().createOrderService();

    private static final Logger log = Logger.getLogger(DeleteProductFromOrder.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in DeleteProductFromOrder");

        Long userId = (Long)request.getSession().getAttribute(ID);
        String bookId = request.getParameter(BOOK_ID);
        Order order = new Order(bookId,userId);
        String lastCommand;
        try {
         if (orderService.deleteFromOrder(order)){
             lastCommand = "frontController?command=go_To_Page&address=main.jsp";
             request.getSession().setAttribute(LAST_COMMAND,lastCommand);
             request.getSession().setAttribute(MESSAGE,"Book is deleted from order");
             response.sendRedirect(lastCommand);
         }else{
             lastCommand = "frontController?command=order_Info";
             request.getSession().setAttribute(LAST_COMMAND,lastCommand);
             request.setAttribute(MESSAGE,"Book wasn't deleted from order");
             request.getRequestDispatcher(lastCommand).forward(request,response);
         }
         }catch (Exception e){
            throw new ControllerException(e);
        }

    }
}

