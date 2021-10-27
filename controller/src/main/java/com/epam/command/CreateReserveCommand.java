package com.epam.command;

import com.epam.ConfigurationManager;
import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateReserveCommand implements AbstractCommand{

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        try{
//            long userId = (Long)request.getSession().getAttribute("id");
            long userId = 1L;
            long productId = 2L;


            Reserve reserve = new Reserve(userId,productId);
            if (reserveService.save(reserve)){
                request.setAttribute("productAddedToOrder","Product successfully added to Order list");

            }else{
                request.setAttribute("errorNoCreateOrder","Order is not added to Order list!");

            }
            request.getRequestDispatcher("/jsp/productInfo.jsp").forward(request,response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new ControllerException(e);
        }
        }

}
