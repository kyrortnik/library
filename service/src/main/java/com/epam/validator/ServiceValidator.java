package com.epam.validator;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;


import static java.util.Objects.isNull;


public class ServiceValidator {

    public void validation(User user) throws ServiceException {
        if (isNull(user) || ("".equals(user.getLogin()) && "".equals(user.getPassword()))) {
            throw new ServiceException("Invalid User");
        }
    }

    public void validation(Reserve reserve) throws ServiceException {
        if (isNull(reserve) || reserve.getProductId() < 0) {
            throw new ServiceException("Invalid Reserve");
        }
    }

    public void validation(Order order) throws ServiceException {
        if (isNull(order) || order.getProductIds().isEmpty()) {
            throw new ServiceException("Invalid Reserve");
        }
    }

    public void validation(Book book) throws ServiceException {
        if (isNull(book)) {
            throw new ServiceException("Null Book");
        } else if (book.getTitle().equals("") && book.getAuthor().equals("")) {
            throw new ServiceException("invalid book");
        }

    }

    public void validation(long id) throws ServiceException {
        if (id < 0) {
            throw new ServiceException("Invalid id.");
        }
    }

    public void validation(String string) throws ServiceException {
        if (isNull(string) || string.isEmpty()) {
            throw new ServiceException("Invalid string.");
        }
    }

    public void validation(Page<Book> bookPageRequest) throws ServiceException {
        if (isNull(bookPageRequest)) {
            throw new ServiceException("BookPageRequest is null");
        }
    }

    public void validation(List parametersList) throws ServiceException {
        if (isNull(parametersList) || parametersList.isEmpty()) {
            throw new ServiceException("Parameters list is null or empty");
        }
    }


}
