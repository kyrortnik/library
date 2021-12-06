package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.Reserve;
import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.ReserveDAO;
import com.epam.validator.ServiceValidator;

import java.util.Objects;


public class ReserveServiceImpl implements ReserveService {

    private final ReserveDAO reserveDAO;
    private final ServiceValidator serviceValidator;

    public ReserveServiceImpl(ReserveDAO reserveDAO, ServiceValidator serviceValidator) {
        this.serviceValidator = serviceValidator;
        this.reserveDAO = reserveDAO;
    }


    @Override
    public boolean save(Reserve reserve) throws ServiceException {
        serviceValidator.validation(reserve);
        try {
            ReserveRow reserveRow = convertToReserveRow(reserve);
            return reserveDAO.save(reserveRow);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean delete(Long userId, Long bookId) throws ServiceException {
        serviceValidator.validation(userId);
        serviceValidator.validation(bookId);
        try {
            return reserveDAO.deleteByUserAndProduct(userId, bookId);

        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }


    private ReserveRow convertToReserveRow(Reserve reserve) {
        ReserveRow row = null;
        if (Objects.nonNull(reserve.getUserId())) {
            row = new ReserveRow();
            row.setId(reserve.getId());
            row.setProductId(reserve.getProductId());
            row.setUserId(reserve.getUserId());
        }
        return row;
    }

}
