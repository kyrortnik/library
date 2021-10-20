package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.entity.Book;
import com.epam.BookService;
import com.epam.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ShowProductsCommand implements AbstractCommand{

    private BookService serviceFactory = ServiceFactory.getInstance().createBookService();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        ArrayList<Book> products =(ArrayList<Book>) serviceFactory.getAll();
        request.setAttribute("products",products);
        page = ConfigurationManager.getProperty("path.page.products");
        return page;
    }
}
