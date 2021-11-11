package com.epam.command;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Order;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class OrderInfoCommand implements AbstractCommand{


    private OrderService orderService = ServiceFactory.getInstance().createOrderService();
    private BookService bookService = ServiceFactory.getInstance().createBookService();

    private static final Logger log = Logger.getLogger(OrderInfoCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{

            log.info("Start in OrderInfoCommand");
            Long userId = (Long)request.getSession().getAttribute("id");
            Order order = orderService.getByUserId(userId);
            if (order == null){
                request.setAttribute("orderMessage","No order created for you yet.");
            }else{
                List<Book> booksFromOrder = bookService.findBooksByOrder(order);
                request.setAttribute("booksFromOrder", booksFromOrder);
                if (booksFromOrder.isEmpty()){
                    request.setAttribute("orderMessage","No books in your order yet.");
                }
                }
            String lastCommand = AbstractCommand.defineCommand(request,false);
            request.getSession().setAttribute("lastCommand",lastCommand);
            request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);


        }catch (IOException | ServletException | ServiceException e ){
            throw new ControllerException(e);
        }



    }
}
