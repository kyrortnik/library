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
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class CreateBookCommand implements Command{

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.createBookService();

    private static final Logger log = Logger.getLogger(CreateBookCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in CreateBookCommand");
        try{
            String lastCommand;
            Book book = getBook(request);
            if (bookService.create(book)){
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                request.getSession().setAttribute(MESSAGE,"Book is created!");
                response.sendRedirect(lastCommand);
            }else{
                lastCommand = "frontController?command=go_To_Page&address=newBook.jsp";
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                request.setAttribute(MESSAGE,"Book with such title already exists!");
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }
    }

    private Book getBook(HttpServletRequest request) {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");

        String publishYear = request.getParameter("publishingYear");
        int publishingYear = isNumeric(publishYear) ? Integer.parseInt(publishYear) : 0;

        String genre = request.getParameter("genre");

        String pages = request.getParameter("numberOfPages");
        int numberOfPages = isNumeric(pages) ? Integer.parseInt(pages) : 0;

        String description = request.getParameter("description");
        boolean isHardCover = request.getParameter("isHardCover").toUpperCase(Locale.ROOT).equals("YES");

        return  new Book(title ,author,publishingYear,publisher,genre,numberOfPages,isHardCover,description);
    }

    private boolean isNumeric(String parameter){
        if (parameter == null){
            return false;
        }
        try{
            int num = Integer.parseInt(parameter);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
