package com.epam.command.impl;

import com.epam.ReserveService;
import com.epam.ServiceFactory;
import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.exception.ServiceException;
import com.epam.validator.ControllerValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;

public class DeleteReserveCommand extends AbstractCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteReserveCommand.class.getName());

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final ReserveService reserveService = serviceFactory.getReserveService();
    private final ControllerValidator controllerValidator = new ControllerValidator();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        LOG.info("Start in DeleteReserveCommand");

        Long userId = (Long)request.getSession().getAttribute(ID);
        String bookIdString = request.getParameter(BOOK_ID);

        controllerValidator.validation(userId);
        controllerValidator.numericParameterValidation(bookIdString);
        Long bookId = Long.valueOf(bookIdString);
        String lastCommand = "frontController?command=go_To_Page&address=main.jsp";
        String message;
        try{
            if (reserveService.delete(userId,bookId)){
                message = "Reserve is deleted";
                successfulProcessRedirect(request,response,lastCommand,message);
            }else{
                message = "Such reserve does not exist!";
               unsuccessfulProcess(request,response,lastCommand,message);
            }
        }catch(ServiceException | ServletException | IOException e){
            throw new ControllerException(e);
        }
    }
}
