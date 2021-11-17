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

import static com.epam.command.util.ControllerConstants.*;

public class DeleteBookCommand implements Command{

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    private static final Logger log = Logger.getLogger(DeleteBookCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in DeleteBookCommand");

        Long bookId = Long.valueOf(request.getParameter(BOOK_ID));
        Book book = new Book(bookId);
        String lastCommand;
        try{
            if (bookService.delete(book)){
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.getSession().setAttribute(MESSAGE,"Book is deleted");
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                response.sendRedirect(lastCommand);
            }else {
                lastCommand = "frontController?command=product_Info?id=" + bookId;
                request.setAttribute(MESSAGE,"No such book exists");
                request.setAttribute(LAST_COMMAND,lastCommand);
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (IOException | ServiceException | ServletException e){
            throw new ControllerException(e);
        }
    }
}
