package com.epam.command;

import com.epam.BookService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class UpdateBookCommand implements Command {

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        long bookId = Long.parseLong(request.getParameter("bookId"));
        String pageForRedirect;
        String title = request.getParameter("title").trim();
        String author = request.getParameter("author").trim();
        String publisher = request.getParameter("publisher").trim();
        int publishingYear = 0;
        if (!request.getParameter("publishingYear").equals("")){
            publishingYear = Integer.parseInt(request.getParameter("publishingYear"));
        }
        boolean isHardCover = request.getParameter("isHardCover").toUpperCase(Locale.ROOT).equals("YES");
        int numberOfPages = 0;
        if (!request.getParameter("numberOfPages").equals("")){
            numberOfPages  = Integer.parseInt(request.getParameter("numberOfPages"));
        }
        String genre = request.getParameter("genre").trim();
        String description = request.getParameter("description").trim();

        Book book = new Book (title,author,publishingYear,publisher,genre,numberOfPages,isHardCover,description);
        String lastCommand = "frontController?command=productInfo&id="+ bookId;
        request.getSession().setAttribute("lastCommand",lastCommand);

        try{
            if (bookService.update(book)){
                pageForRedirect = "frontController?command=goToPage&address=main.jsp";
            request.getSession().setAttribute("message","Book is updated");
            response.sendRedirect(pageForRedirect);
            }else {
                request.setAttribute("reserveErrorMessage","No such book exists");
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch (Exception e){
            throw new ControllerException(e);
        }

    }
}
