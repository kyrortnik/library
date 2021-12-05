package com.epam.validator;

import com.epam.command.exception.ControllerException;
import com.epam.entity.Book;
import com.epam.entity.Page;

import static java.util.Objects.isNull;

public class ControllerValidator {

    public void numericParameterValidation(String... numericParameters) throws ControllerException {
        for (String numericParameter : numericParameters) {
            if (!isNumeric(numericParameter)) {
                throw new ControllerException("Invalid numeric parameter");
            }
        }

    }

    public void stringParameterValidation(String... parameters) throws ControllerException {
        for (String parameter : parameters) {
            if (isNull(parameter) || parameter.isEmpty()) {
                throw new ControllerException("Title and author can't be empty");
            }
        }

    }

    public void stringParameterValidationNonNull(String... parameters) throws ControllerException {
        for (String parameter : parameters) {
            if (isNull(parameter)) {
                throw new ControllerException("Invalid input parameter");
            }
        }

    }

    public void longValidation(Long... ids) throws ControllerException {
        for (Long id : ids) {
            if (isNull(id) || id < 0) {
                throw new ControllerException("Invalid id.");
            }
        }

    }

    public void pageValidation(Page<Book> bookPageRequest) throws ControllerException {
        boolean isValidPage =
                bookPageRequest.getPageNumber() != 0
                        && !bookPageRequest.getDirection().isEmpty()
                        && !bookPageRequest.getSortBy().isEmpty();
        if (!isValidPage) {
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
