package com.epam.command;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CreateReserveCommand implements AbstractCommand {

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

    private static final Logger log = Logger.getLogger(AddToOrderCommand.class.getName());

    //    TODO proper implementation of goToPageCommand
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        log.info("Start in CreateReserveCommand");


        try {
            Long userId = (Long) request.getSession().getAttribute("id");
            Long productId = Long.valueOf(request.getParameter("bookId"));
            String pageForRedirect;

            Reserve reserve = new Reserve(userId, productId);
//            if (!reserveService.productExistsInOrder(reserve)) {
                if (reserveService.save(reserve)) {
                    request.setAttribute("message", "Product successfully added to Order list");
                    pageForRedirect = "frontController?command=goToPage&address=main.jsp";
                } else {
                    request.setAttribute("message", "Product is not added to Reserve list! Order for you already exists.");
                    pageForRedirect = "frontController?command=goToPage&address=productInfo.jsp";
                }

                response.sendRedirect(pageForRedirect);
            /*else{
                request.setAttribute("errorNoCreateOrder","Order is not added to Order list! Such order already exists.");

            }*/

            /*} else {

            }*/
        } catch (ServiceException | IOException /*| ServletException*/ e) {
            throw new ControllerException(e);

        }
    }
}