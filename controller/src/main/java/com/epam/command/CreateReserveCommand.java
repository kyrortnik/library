package com.epam.command;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateReserveCommand implements AbstractCommand{

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

//    TODO proper implementation of goToPageCommand
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{


        try{
           Long userId = (Long)request.getSession().getAttribute("id");
           Long productId = Long.valueOf(request.getParameter("productId"));
            String lastCommand;


            Reserve reserve = new Reserve(userId,productId);
            if (reserveService.save(reserve)){
                request.setAttribute("message","Product successfully added to Order list");
                 lastCommand = "frontController?command=goToPage&address=productInfo.jsp";
                } else {
                request.setAttribute("message","Product is not added to Reserve list! Order for you already exists.");
                    lastCommand = "frontController?command=goToPage&address=productInfo.jsp";
                }
            response.sendRedirect(lastCommand);
            }/*else{
                request.setAttribute("errorNoCreateOrder","Order is not added to Order list! Such order already exists.");

            }*/
//            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);


         catch (ServiceException | IOException /*| ServletException*/ e) {
            throw new ControllerException(e);
        }
        }

}
