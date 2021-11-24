package com.epam.command.impl;

import com.epam.command.AbstractCommand;
import com.epam.command.Command;
import com.epam.command.exception.ControllerException;
import com.epam.entity.Page;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.util.ControllerConstants.*;


public class ShowUsersCommand extends AbstractCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final Logger LOG = Logger.getLogger(ShowUsersCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        LOG.info("Start in ShowUsersCommand");

        try{
            int currentPage = getCurrentPage(request);


            Page<UserDTO> pageableRequest = new Page<>();
            pageableRequest.setPageNumber(currentPage);
            pageableRequest.setSortBy("login");
            Page<UserDTO> pageable = userService.getUsersPage(pageableRequest);
            String lastCommand = defineLastCommand(request,true);

            request.setAttribute("pageableUsers",pageable);
            request.getSession().setAttribute(LAST_COMMAND,lastCommand);
            request.getRequestDispatcher("frontController?command=go_To_Page&address=main.jsp").forward(request,response);
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }

    }

    private int getCurrentPage(HttpServletRequest request) {
        String currentPageParam = request.getParameter("currentPageUser");
        if (Objects.isNull(currentPageParam)) {
            currentPageParam = "1";
        }

        return Integer.parseInt(currentPageParam);
    }
}
