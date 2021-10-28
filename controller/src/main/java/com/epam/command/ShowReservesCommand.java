package com.epam.command;

import com.epam.BookService;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Reserve;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowReservesCommand implements AbstractCommand{

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();
    private BookService bookService = ServiceFactory.getInstance().createBookService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

//        Long userId = request.getSession().getAttribute("id");
        try{
            Long userId = 1L;

            List<Reserve> reserves = reserveService.getReservesForUser(userId);
            List<Book> books = bookService.findBooksByIds(reserves);
            request.setAttribute("products",books);
            request.getRequestDispatcher("/jsp/orderList.jsp").forward(request,response);
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }
}
