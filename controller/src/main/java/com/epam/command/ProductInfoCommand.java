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

public class ProductInfoCommand extends AbstractCommand {


    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BookService bookService = serviceFactory.createBookService();

    private static final Logger log = Logger.getLogger(ProductInfoCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in ProductInfoCommand");

        try {
            Book book = bookService.findById(Long.parseLong(request.getParameter(ID)));

            if(book != null){
                request.setAttribute("book", book);
            } else {
                request.setAttribute(MESSAGE, "No such product was found");
            }

            String lastCommand = defineCommand(request,false);
            request.getSession().setAttribute(LAST_COMMAND,lastCommand);
            request.getSession().setAttribute(MESSAGE,null);
            request.getRequestDispatcher("frontController?command=go_To_Page&address=productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
    }
}
