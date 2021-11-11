package com.epam.command;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Reserve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteReserveCommand implements Command {

    private ReserveService reserveService = ServiceFactory.getInstance().createReserveService();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {

        Long userId = (Long)request.getSession().getAttribute("id");
        Long bookId = Long.valueOf(request.getParameter("bookId"));
        Reserve reserve = new Reserve(userId,bookId);
        String pageToRedirect = "frontController?command=showReserves";


        try{
            if (reserveService.delete(reserve)){
                request.getSession().setAttribute("deleteMessage","Reserve is deleted");
                response.sendRedirect(pageToRedirect);
            }else{
                request.getSession().setAttribute("deleteMessage","Such reserve does not exist!");
                request.getRequestDispatcher(pageToRedirect).forward(request,response);
            }
        }catch(Exception e){
            throw new ControllerException(e);
        }
    }
}
