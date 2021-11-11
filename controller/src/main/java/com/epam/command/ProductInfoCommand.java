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
import java.util.logging.Logger;

public class ProductInfoCommand implements Command {

    private static final String MESSAGE = "message";
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BookService bookService = serviceFactory.createBookService();
    private static final Logger log = Logger.getLogger(ProductInfoCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {


        try {
            log.info("Start in ProductInfoCommand");
            Book book = bookService.findById(Long.parseLong(request.getParameter("id")));

            if(book != null){
                request.setAttribute("book", book);
            } else {
                request.setAttribute("noSuchProduct", "No such product was found");
            }

            String lastCommand = Command.defineCommand(request,false);
            request.getSession().setAttribute("lastCommand",lastCommand);
           request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
//        return page;
    }
}
