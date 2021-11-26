package com.epam.command.impl;

import com.epam.BookService;
import com.epam.OrderService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.ID;
import static java.util.Objects.nonNull;

public class OrderInfoCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(OrderInfoCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private final BookService bookService = serviceFactory.getBookService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in OrderInfoCommand");

        String lastCommand;
        String message;
        Long userId = (Long) request.getSession().getAttribute(ID);
        controllerValidator.validation(userId);

       /* try{
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
        }*/
        try {
            List<Book> booksFromOrder = bookService.getBooksFromOrder(userId);
            if (nonNull(booksFromOrder) || !booksFromOrder.isEmpty()) {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.setAttribute("booksFromOrder", booksFromOrder);
                successfulProcessForward(request, response, lastCommand, null);
            } else {
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                message = " There's not order for you or no books in your order yet.";
                unsuccessfulProcess(request, response, lastCommand, message);
            }
        } catch (IOException | ServiceException | ServletException e) {
            throw new ControllerException(e);
        }


    }
}
