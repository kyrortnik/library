package com.epam.validator;

import com.epam.command.exception.ControllerException;
import com.epam.entity.*;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ControllerValidator {

    public void numericParameterValidation(String... numericParameters) throws ControllerException {
        for (String numericParameter : numericParameters) {
            if (isNull(numericParameter) || !isNumeric(numericParameter)) {
                throw new ControllerException("Invalid numeric parameter");
            }
        }

    }

    public void stringParameterValidation(String... parameters) throws ControllerException {
        for (String parameter : parameters) {
            if (isNull(parameter) || parameter.isEmpty()) {
                throw new ControllerException("Invalid input parameter");
            }
        }

    }

    public void listParameterValidator(List parameters) throws ControllerException {
        if (isNull(parameters) || parameters.isEmpty()) {
            throw new ControllerException("Invalid list parameter");
        }
    }


    public void validation(User user) throws ControllerException {
        if (isNull(user) || ("".equals(user.getLogin()) && "".equals(user.getPassword()))) {
            throw new ControllerException("Invalid User");
        }
    }

    public void validation(Reserve reserve) throws ControllerException {
        if (Objects.isNull(reserve) || reserve.getProductId() < 0) {
            throw new ControllerException("Invalid Reserve");
        }
    }

    public void validation(Order order) throws ControllerException {
        if (Objects.isNull(order) || order.getProductIds().equals("")) {
            throw new ControllerException("Invalid Reserve");
        }
    }

    public void validation(Book book) throws ControllerException {
        if (Objects.isNull(book)) {
            throw new ControllerException("Null Book");
        } else if (book.getTitle().equals("") && book.getAuthor().equals("")) {
            throw new ControllerException("invalid book");
        }

    }

    public void validation(Long... ids) throws ControllerException {
        for (Long id : ids){
            if (isNull(id) || id < 0) {
                throw new ControllerException("Invalid id.");
            }
        }

    }

    public void validation(Page<Book> bookPageRequest) throws ControllerException {
        if (Objects.isNull(bookPageRequest)) {
            throw new ControllerException("BookPageRequest is null");
        }
    }

    private boolean isNumeric(String parameter) {
        boolean isNumeric = true;
        try {
            Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            isNumeric = false;
        }
        return isNumeric;
    }
}
