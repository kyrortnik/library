package com.epam.command;

import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.entity.User;
import com.epam.ServiceFactory;
import com.epam.UserService;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import static com.epam.command.util.ControllerConstants.MAX_ROWS;

public class ShowUsersCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.createUserService();
    private static final Logger log = Logger.getLogger(ShowUsersCommand.class.getName());


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException{

        log.info("Start in ShowUsersCommand");


           /* try{
                ArrayList<User> users = (ArrayList<User>)userService.getUsers();
                request.setAttribute("users",users);
                response.sendRedirect("/jsp/users.jsp");
            }catch (IOException | ServiceException e){
                throw new ControllerException(e);
            }*/

        try{
            String currentPageParam = request.getParameter("currentPageUser");
            if (Objects.isNull(currentPageParam)) {
                currentPageParam = "1";
            }

            int currentPage = Integer.parseInt(currentPageParam);


            Page<UserDTO> pageableRequest = new Page<>();
            pageableRequest.setPageNumber(currentPage);
            pageableRequest.setLimit(MAX_ROWS);
            pageableRequest.setSortBy("login");


            Page<UserDTO> pageable = userService.getAll(pageableRequest);

            String lastCommand = Command.defineCommand(request,true);

            request.setAttribute("pageableUsers",pageable);
            request.getSession().setAttribute("lastCommand",lastCommand);

//            final Pageable<Book> pageable = bookService.showProducts(pageableRequest);

            // send response
//            request.setAttribute(PAGEABLE, pageable);
//            request.getRequestDispatcher(Command.prepareUri(request) + JSP).forward(request, response);
//            // response.sendRedirect(ConfigurationManager.getProperty("path.page.products"));

            //           request.getRequestDispatcher(Command.prepareUri(request) + JSP).forward(request, response);
            request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
        }catch (IOException | ServletException | ServiceException e){
            throw new ControllerException(e);
        }



//            return page;

    }
}
