package com.epam.command;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.epam.command.util.ControllerConstants.*;

public class UpdateBookCommand implements Command {

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        long bookId = Long.parseLong(request.getParameter(BOOK_ID));
        Book book = getBook(request);
        String lastCommand;

        try{
            if (bookService.update(book)){
                lastCommand = "frontController?command=go_To_Page&address=main.jsp";
                request.getSession().setAttribute(MESSAGE,"Book is updated");
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                response.sendRedirect(lastCommand);
            }else {
                lastCommand = "frontController?command=productInfo&id="+ bookId;
                request.setAttribute(MESSAGE,"No such book exists");
                request.getSession().setAttribute(LAST_COMMAND,lastCommand);
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (Exception e){
            throw new ControllerException(e);
        }
    }

    private Book getBook(HttpServletRequest request) {
        String title = request.getParameter("title").trim();
        String author = request.getParameter("author").trim();
        String publisher = request.getParameter("publisher").trim();

        String publishYear = request.getParameter("publishingYear");
        int publishingYear = isNumeric(publishYear) ? Integer.parseInt(publishYear) : 0;

        boolean isHardCover = request.getParameter("isHardCover").toUpperCase(Locale.ROOT).equals("YES");

        String pages = request.getParameter("numberOfPages");
        int numberOfPages = isNumeric(pages) ? Integer.parseInt(pages) : 0;

        String genre = request.getParameter("genre").trim();
        String description = request.getParameter("description").trim();

        return new Book (title,author,publishingYear,publisher,genre,numberOfPages,isHardCover,description);
    }

    private boolean isNumeric(String parameter){
        if (parameter == null){
            return false;
        }
        try{
            Integer.parseInt(parameter);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
