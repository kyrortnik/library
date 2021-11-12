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
import java.util.Locale;

public class CreateBookCommand implements Command{

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        try{
            String pageToRedirect;
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            int publishingYear = 0;
            if (!request.getParameter("publishingYear").equals("")){
                publishingYear = Integer.parseInt(request.getParameter("publishingYear"));
            }
            String genre = request.getParameter("genre");
            int numberOfPages = 0;
            if (!request.getParameter("numberOfPages").equals("")){
                numberOfPages  = Integer.parseInt(request.getParameter("numberOfPages"));
            }
            String description = request.getParameter("description");
            boolean isHardCover;
            isHardCover = request.getParameter("isHardCover").toUpperCase(Locale.ROOT).equals("YES");

            Book book = new Book(author,title,publishingYear,publisher,genre,numberOfPages,isHardCover,description);
            if (bookService.create(book)){
                pageToRedirect = "frontController?command=goToPage&address=main.jsp";
                request.getSession().setAttribute("lastCommand",pageToRedirect);
                request.getSession().setAttribute("message","Book is created!");
                response.sendRedirect(pageToRedirect);
            }else{
                pageToRedirect = "frontController?command=goToPage&address=newBook.jsp";
                request.getSession().setAttribute("lastCommand",pageToRedirect);
                request.setAttribute("message","Book with such title already exists!");
                request.getRequestDispatcher(pageToRedirect).forward(request,response);
            }
        }catch (ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }


    }
}
