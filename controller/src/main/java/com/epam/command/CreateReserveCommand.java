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

public class CreateReserveCommand implements Command {

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

    private static final Logger log = Logger.getLogger(CreateReserveCommand.class.getName());

    //    TODO proper implementation of goToPageCommand
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateReserveCommand");


        try {

            Long userId = (Long) request.getSession().getAttribute("id");
            Long bookId = Long.valueOf(request.getParameter("bookId"));
            String pageForRedirect;

            Reserve reserve = new Reserve(userId, bookId);
            String lastCommand = Command.defineCommand(request,false);
            request.getSession().setAttribute("lastCommand",lastCommand);
            request.setAttribute("bookId",bookId);
//            if (!reserveService.productExistsInOrder(reserve)) {
                if (reserveService.save(reserve)) {
//                    request.setAttribute("reserveMessage", "Product successfully added to Order list");
//                    request.getSession().setAttribute("message", "Product successfully added to Reserve list");
                    pageForRedirect = "frontController?command=goToPage&address=main.jsp";
                    response.sendRedirect(pageForRedirect);
                } else {
//                    request.setAttribute("reserveMessage", "Product is not added to Reserve list! Order for you already exists.");
                    request.setAttribute("reserveErrorMessage","Product is not added to Reserve list! Order for you already exists.");
//                    request.setAttribute("backURL","frontController?command=goToPage&address=productInfo.jsp" + "&bookId=" + bookId);
                    pageForRedirect = "frontController?command=goToPage&address=productInfo.jsp";
//                    pageForRedirect = (String)request.getSession().getAttribute("lastCommand");
                    request.getRequestDispatcher(pageForRedirect).forward(request,response);
                }
        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);

        }
    }
}