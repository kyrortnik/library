package com.epam.command.impl;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Order;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.ID;
import static com.epam.util.ControllerConstants.ORDER_MESSAGE;

public class OrderInfoCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private final BookService bookService = serviceFactory.getBookService();

    private static final Logger LOG = Logger.getLogger(OrderInfoCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in OrderInfoCommand");

        String lastCommand;
        Long userId = (Long)request.getSession().getAttribute(ID);


        try{
            Order order = orderService.getByUserId(userId);
            if (order == null){
                request.setAttribute(ORDER_MESSAGE,"No order created for you yet.");
            }else{
                List<Book> booksFromOrder = bookService.findBooksByOrder(order);
                request.setAttribute("booksFromOrder", booksFromOrder);
                if (booksFromOrder.isEmpty()){
                    request.setAttribute(ORDER_MESSAGE,"No books in your order yet.");
                }
                }
             lastCommand = defineLastCommand(request,false);
            request.getSession().setAttribute("lastCommand",lastCommand);
            request.getRequestDispatcher("frontController?command=go_To_Page&address=main.jsp").forward(request, response);


        }catch (IOException | ServletException | ServiceException e ){
            throw new ControllerException(e);
        }



    }
}
