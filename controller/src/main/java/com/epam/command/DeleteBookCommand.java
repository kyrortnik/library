package com.epam.command;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBookCommand implements Command{

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        Long bookId = Long.valueOf(request.getParameter("bookId"));
        Book book = new Book(bookId);
        String lastCommand = "frontController?command=productInfo?id=" + bookId;
//        String pageForRedirect;
        try{
            if (bookService.delete(book)){
                lastCommand = "frontController?command=goToPage&address=main.jsp";
                request.getSession().setAttribute("message","Book is deleted");
                response.sendRedirect(lastCommand);
            }else {
                request.setAttribute("reserveErrorMessage","No such book exists");
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (Exception e){
            throw new ControllerException(e);
        }
    }
}
