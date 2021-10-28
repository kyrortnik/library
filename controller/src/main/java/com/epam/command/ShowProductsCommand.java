package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.BookService;
import com.epam.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ShowProductsCommand implements AbstractCommand{

    private BookService serviceFactory = ServiceFactory.getInstance().createBookService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{
//        String page;
        try{
            ArrayList<Book> products =(ArrayList<Book>) serviceFactory.getAll();
            request.setAttribute("products",products);
            // response.sendRedirect(ConfigurationManager.getProperty("path.page.products"));
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.products")).forward(request,response);
        }catch (IOException  | ServletException e){
            throw new ControllerException(e);
        }


//        return page;
    }
}
