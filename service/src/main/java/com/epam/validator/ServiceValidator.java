package com.epam.validator;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.Objects;

public class ServiceValidator {

    public static boolean validation(User user) {
        return Objects.isNull(user) || "".equals(user.getLogin()) && "".equals(user.getPassword());
    }

    public static boolean validation(Reserve reserve){
        return Objects.isNull(reserve) || reserve.getProductId() < 0;
    }

    public static boolean validation(Order order){
        return Objects.isNull(order) || !order.getProductIds().equals("");
    }

    public static void validation(Book book) throws ServiceException {
        if (Objects.isNull(book)){
            throw new ServiceException("Empty book");
        }
        else if (book.getTitle().equals("") && book.getAuthor().equals("")){
            throw new ServiceException("Empty book");
        }

    }

    public static boolean validation(long id) {
        return id > 0;
    }

    public static void validation( Page<Book> bookPageRequest) throws ServiceException {
        if (Objects.isNull(bookPageRequest)) {
            throw new ServiceException("BookPageRequest is null or empty.");
        }
    }




}
