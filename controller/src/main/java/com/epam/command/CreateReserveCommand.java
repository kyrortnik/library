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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{
           Long userId = (Long)request.getSession().getAttribute("id");
           Long productId = Long.valueOf(request.getParameter("productId"));


            Reserve reserve = new Reserve(userId,productId);
            if (reserveService.save(reserve)){
                request.setAttribute("productAddedToOrder","Product successfully added to Order list");

            }else{
                request.setAttribute("errorNoCreateOrder","Order is not added to Order list! Such order already exists.");

            }
            request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
        }

}
