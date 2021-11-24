package com.epam.validator;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.Objects;

import static java.util.Objects.isNull;


public class ServiceValidator {

    public static void validation(User user) throws ServiceException {
        if (isNull(user) || ("".equals(user.getLogin()) && "".equals(user.getPassword()))){
            throw new ServiceException("Invalid User");
        }
    }

    public static void validation(Reserve reserve) throws ServiceException{
        if (Objects.isNull(reserve) || reserve.getProductId() < 0){
            throw  new ServiceException("Invalid Reserve");
        }
    }

    public static void validation(Order order) throws ServiceException{
        if (Objects.isNull(order) || order.getProductIds().equals("")){
            throw  new ServiceException("Invalid Reserve");
        }
    }

    public static void validation(Book book) throws ServiceException {
        if (Objects.isNull(book)){
            throw new ServiceException("Null Book");
        }
        else if (book.getTitle().equals("") && book.getAuthor().equals("")){
            throw new ServiceException("invalid book");
        }

    }

    public static void validation(long id) throws  ServiceException{
       if(id < 0 ){
           throw new ServiceException("Invalid id.");
       }
    }

    public static void validation( Page<Book> bookPageRequest) throws ServiceException {
        if (Objects.isNull(bookPageRequest)) {
            throw new ServiceException("BookPageRequest is null");
        }
    }




}
