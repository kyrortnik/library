package com.epam.command;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.*;

public class DeleteReserveCommand implements Command {

    private final ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

    private static final Logger log = Logger.getLogger(DeleteReserveCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        log.info("Start in DeleteReserveCommand");

        Long userId = (Long)request.getSession().getAttribute(ID);
        Long bookId = Long.valueOf(request.getParameter(BOOK_ID));
        Reserve reserve = new Reserve(userId,bookId);
        String lastCommand = "frontController?command=go_To_Page&address=main.jsp";
//        String lastCommand = "/jsp/main.jsp";
        request.getSession().setAttribute(LAST_COMMAND,lastCommand);

        try{
            if (reserveService.delete(reserve)){
                request.getSession().setAttribute(MESSAGE,"Reserve is deleted");
                response.sendRedirect(lastCommand);
            }else{
                request.getSession().setAttribute(MESSAGE,"Such reserve does not exist!");
                request.getRequestDispatcher(lastCommand).forward(request,response);
            }
        }catch(ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }
    }
}
