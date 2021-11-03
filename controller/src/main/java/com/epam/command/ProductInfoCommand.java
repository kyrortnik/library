package com.epam.command;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductInfoCommand implements AbstractCommand {

    private static final String MESSAGE = "message";
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BookService bookService = serviceFactory.createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

//        String page;

        try {
            Book book = bookService.findById(Long.parseLong(request.getParameter("id")));

            if(book != null){
                request.setAttribute("book", book);
            } else {
                request.setAttribute("noSuchProduct", "No such product was found");
            }

//            request.setAttribute("message", request.getParameter(MESSAGE));
           request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
//        return page;
    }
}
