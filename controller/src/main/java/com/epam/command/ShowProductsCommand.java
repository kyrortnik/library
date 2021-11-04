package com.epam.command;


import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.entity.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class ShowProductsCommand implements AbstractCommand{

    private BookService bookService = ServiceFactory.getInstance().createBookService();
    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{
//        String page;
        log.info("Start in ShowProductsCommand");
        try{
           String currentPageParam = request.getParameter("currentPage");
            if (Objects.isNull(currentPageParam)) {
                currentPageParam = "1";
            }
//           String currentPageParam = "2";
//            if(/*validation on null/empty*/){
//            currentPage = "1";authType = null
//            }
//            String currentLimitParam = request.getParameter("pageLimit");
//            if(/*validation on null/empty*/){
//            currentLimitParam = "10";
//            }

            int currentPage = Integer.parseInt(currentPageParam);
//
            int currentLimit = MAX_ROWS;

 /*           ArrayList<Book> products =(ArrayList<Book>) bookService.getAll();
            request.setAttribute("products",products);*/

            Page<Book> pageableRequest = new Page<>();
            pageableRequest.setPageNumber(currentPage);
            pageableRequest.setLimit(currentLimit);

             Page<Book> pageable = bookService.getAll(pageableRequest);

             request.setAttribute("pageable",pageable);


//            final Pageable<Book> pageable = bookService.showProducts(pageableRequest);

            // send response
//            request.setAttribute(PAGEABLE, pageable);
//            request.getRequestDispatcher(Command.prepareUri(request) + JSP).forward(request, response);
//            // response.sendRedirect(ConfigurationManager.getProperty("path.page.products"));

 //           request.getRequestDispatcher(Command.prepareUri(request) + JSP).forward(request, response);
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }catch (IOException  | ServletException e){
            throw new ControllerException(e);
        }


//        return page;
    }
}
