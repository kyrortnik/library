package com.epam.command;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Order;

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

    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {



        try{
            log.info("Start in OrderInfoCommand");
            Long userId = (Long)request.getSession().getAttribute("id");
            Order order = orderService.getByUserId(userId);

            List<Book> booksFromOrder = bookService.findBooksByOrder(order);
           /* String[] booksID = order.getProductIds().split(" ");
            Integer[] booksId = new Integer[booksID.length];
            for (int i = 0;i<booksID.length;i++){
                booksId[i] = Integer.parseInt(booksID[i]);
            }*/


                if (userId.equals(order.getUserId())) {
                    request.setAttribute("booksFromOrder", booksFromOrder);
                } else {
                    request.setAttribute("orderMessage", "No order created for you yet.");
                }
                request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);

        }catch (IOException | ServletException e ){
            throw new ControllerException(e);
        }



    }
}
