package com.epam.command;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteProductFromOrder implements Command{

    private final OrderService orderService = ServiceFactory.getInstance().createOrderService();
    private BookService bookService = ServiceFactory.getInstance().createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        Long userId = (Long)request.getSession().getAttribute("id");
        String bookId = request.getParameter("bookId");
        Order order = new Order(bookId,userId);
        String lastCommand = "frontController?command=orderInfo";
        request.getSession().setAttribute("lastCommand",lastCommand);

        try {
         if (orderService.deleteFromOrder(order)){
//             List<Book> booksFromOrder = bookService.findBooksByOrder(order);
//             request.setAttribute("booksFromOrder", booksFromOrder);
//             if (booksFromOrder.isEmpty()){
                 request.setAttribute("orderMessage","No books in your order yet.");
                 response.sendRedirect(lastCommand);
//             }
         }else{
             request.setAttribute("message","donno what happened");
             request.getRequestDispatcher(lastCommand).forward(request,response);
         }
         }catch (Exception e){
            throw new ControllerException(e);
        }

        }


    }

