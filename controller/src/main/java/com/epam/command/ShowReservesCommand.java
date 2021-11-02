package com.epam.command;

import com.epam.BookService;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.BookRow;
import com.epam.entity.Page;
import com.epam.entity.Reserve;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowReservesCommand implements AbstractCommand{

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();
    private BookService bookService = ServiceFactory.getInstance().createBookService();

    private static int PAGE_LIMIT = 2;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

       Long userId = (Long)request.getSession().getAttribute("id");
            try{

                String currentPageParam = request.getParameter("currentPageReserve");
                if (Objects.isNull(currentPageParam)) {
                    currentPageParam = "1";
                }
                int currentPage = Integer.parseInt(currentPageParam);
                int currentLimit = PAGE_LIMIT;

               // Page<Reserve> pageableRequest = new Page<>();


                Page<Book> pageableReserves = new Page<>();

                List<Reserve> reservesForUser = reserveService.getReservesForUser(userId);
                pageableReserves = bookService.findBooksByIds(reservesForUser);
                pageableReserves.setPageNumber(currentPage);
                pageableReserves.setLimit(currentLimit);


                request.setAttribute("pageableReserves",pageableReserves);


          /*  List<Reserve> reserves = reserveService.getReservesForUser(userId);
            List<BookRow> bookRows = bookService.findBooksByIds(reserves);
            request.setAttribute("products", bookRows);*/
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }
}
