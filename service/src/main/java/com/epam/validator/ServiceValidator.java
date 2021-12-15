package com.epam.validator;

import com.epam.entity.Book;
import com.epam.entity.Page;
import com.epam.entity.Reserve;
import com.epam.entity.User;
import com.epam.exception.ServiceException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class ServiceValidator {

    private ServiceValidator() {
    }

    private final static ServiceValidator INSTANCE = new ServiceValidator();

    public static ServiceValidator getInstance() {
        return INSTANCE;
    }


    public void validation(User user) throws ServiceException {
        if (isNull(user) || ("".equals(user.getLogin()) && user.getPassword().length == 0)) {
            throw new ServiceException("Invalid User");
        }
    }

    public void validation(Reserve reserve) throws ServiceException {
        if (isNull(reserve) || reserve.getProductId() < 0) {
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

    public void validation(char[] charArray) throws ServiceException {
        if (isNull(charArray) || charArray.length == 0) {
            throw new ServiceException("Invalid string.");
        }
    }

    public void validation(Page<?> bookPageRequest) throws ServiceException {
        if (isNull(bookPageRequest)) {
            throw new ServiceException("BookPageRequest is null");
        }
        boolean isValidPage = nonNull(bookPageRequest.getPageNumber()) && nonNull(bookPageRequest.getSortBy()) && nonNull(bookPageRequest.getDirection());
        if (!isValidPage) {
            throw new ServiceException("BookPageRequest is null");
        }
    }

    public void validation(List<?> parametersList) throws ServiceException {
        if (isNull(parametersList) || parametersList.isEmpty()) {
            throw new ServiceException("Parameters list is null or empty");
        }
    }


}
