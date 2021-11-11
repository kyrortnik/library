package com.epam.command;

import com.epam.BookService;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ShowReservesCommand implements AbstractCommand{

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();
    private BookService bookService = ServiceFactory.getInstance().createBookService();
    private static final Logger log = Logger.getLogger(ShowReservesCommand.class.getName());

    private static int MAX_ROWS = 5;


    /*@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in ShowReservesCommand");

       Long userId = (Long)request.getSession().getAttribute("id");
            try{

                String currentPageParam = request.getParameter("currentPageReserve");
                if (Objects.isNull(currentPageParam)) {
                    currentPageParam = "1";
                }
                int currentPage = Integer.parseInt(currentPageParam);
                int currentLimit = MAX_ROWS;


                Page<Book> pageableReserves = new Page<>();
                Page<Book> filteredPageableReserves = new Page<>();

                pageableReserves.setPageNumber(currentPage);
                pageableReserves.setLimit(currentLimit);

                List<Reserve> reservesForUser = reserveService.getReservesForUser(userId);
                pageableReserves = bookService.findBooksByIds(reservesForUser);
                 filteredPageableReserves = bookService.getPageByFilter(pageableReserves);
                if (pageableReserves.getElements().isEmpty()){
                request.setAttribute("reservesMessage","No reserves for you yet.");
                }
                request.setAttribute("pageableReserves", pageableReserves);




          *//*  List<Reserve> reserves = reserveService.getReservesForUser(userId);
            List<BookRow> bookRows = bookService.findBooksByIds(reserves);
            request.setAttribute("products", bookRows);*//*
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }catch (IOException | ServletException e){
            throw new ControllerException(e);
        }

    }*/


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in ShowReservesCommand");

        Long userId = (Long)request.getSession().getAttribute("id");
        try{

            String currentPageParam = request.getParameter("currentPageReserve");
            if (Objects.isNull(currentPageParam)) {
                currentPageParam = "1";
            }
            int currentPage = Integer.parseInt(currentPageParam);
            int numberOfReserves = reserveService.countReservesForUser(userId);

            Page<Book> pageableReserves = new Page<>(currentPage,numberOfReserves);
            List<Reserve> reservesForUser = reserveService.findReservationsByUserId(userId,pageableReserves.getOffset());
            List<Book> booksForUser = bookService.findBooksByIds(reservesForUser);
            pageableReserves.setElements(booksForUser);
            pageableReserves.setLimit(MAX_ROWS);
            pageableReserves.setPageNumber(currentPage);
            pageableReserves.setTotalElements(numberOfReserves);

            String lastCommand = AbstractCommand.defineCommand(request,true);
            request.getSession().setAttribute("lastCommand",lastCommand);

            if (pageableReserves.getElements().isEmpty()){
                request.setAttribute("reservesMessage","No reserves for you yet.");
            }else{
                request.setAttribute("pageableReserves", pageableReserves);
            }
           request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }

    }

}
