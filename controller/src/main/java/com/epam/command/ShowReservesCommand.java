package com.epam.command;

import com.epam.BookService;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class ShowReservesCommand extends AbstractCommand {

    private final ReserveService reserveService = ServiceFactory.getInstance().createReserveService();
    private final BookService bookService = ServiceFactory.getInstance().createBookService();
    private  static final Logger log = Logger.getLogger(ShowReservesCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in ShowReservesCommand");
        try{
            Long userId = (Long)request.getSession().getAttribute(ID);
            int currentPage = getCurrentPage(request);

            Page<Book> pageableReserves = getPageableReserve(userId, currentPage);
            String lastCommand = defineCommand(request,true);
            request.getSession().setAttribute(LAST_COMMAND,lastCommand);
            request.getSession().setAttribute(MESSAGE,null);

            if (pageableReserves.getElements().isEmpty()){
                request.setAttribute(RESERVE_MESSAGE,"No reserves for you yet.");
            }else{
                request.setAttribute("pageableReserves", pageableReserves);
            }
           request.getRequestDispatcher("frontController?command=go_To_Page&address=main.jsp").forward(request,response);
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }

    }

    private Page<Book> getPageableReserve(Long userId, int currentPage) throws ServiceException {
        int numberOfReserves = reserveService.countReservesForUser(userId);
        Page<Book> pageableReserves = new Page<>(currentPage, numberOfReserves);
        List<Reserve> reservesForUser = reserveService.getReservesByUserId(userId,pageableReserves.getOffset());
        List<Book> booksForUser = bookService.findBooksByIds(reservesForUser);
        pageableReserves.setElements(booksForUser);
        pageableReserves.setPageNumber(currentPage);
        pageableReserves.setTotalElements(numberOfReserves);
        return pageableReserves;
    }

    private int getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter("currentPageReserve");
        if (Objects.isNull(currentPageParam)) {
            currentPageParam = "1";
        }
        return Integer.parseInt(currentPageParam);
    }

}
