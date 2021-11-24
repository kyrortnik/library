package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.ReserveDAO;
import com.epam.validator.ServiceValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ReserveServiceImpl implements ReserveService {

    private static final Logger LOG = Logger.getLogger(ReserveServiceImpl.class.getName());

    private final ReserveDAO reserveDAO;
    private final ServiceValidator serviceValidator = new ServiceValidator();

    public ReserveServiceImpl(ReserveDAO reserveDAO) {
        this.reserveDAO = reserveDAO;
    }

    /*@Override
    public boolean save(Reserve reserve) throws ServiceException {
        serviceValidator.validation(reserve);
        try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            ReserveRow foundReserveRow = reserveDAO.getByUserAndProductId(reserveRow);
            if (Objects.isNull(foundReserveRow)){
                return reserveDAO.save(reserveRow);
            }else {
                return false;
            }
        }catch (DAOException e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }*/

    @Override
    public boolean save(Reserve reserve) throws ServiceException {
        serviceValidator.validation(reserve);
        try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            return  reserveDAO.save(reserveRow);
        }catch (DAOException e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean delete(Long userId,Long bookId) throws ServiceException {
        serviceValidator.validation(userId);
        serviceValidator.validation(bookId);
        try{
              return reserveDAO.deleteByUserAndProduct(userId,bookId);

        }catch (Exception e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public boolean deleteReservesByUserId(Long userId) throws ServiceException {
        serviceValidator.validation(userId);
        try{
            return reserveDAO.deleteByUserId(userId);
        }catch (DAOException e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Reserve> getReservesForUser(Long userId) throws ServiceException {
       try{
            return convertToReserves(reserveDAO.findReservesForUser(userId));
        }catch (DAOException e){
           LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public int countReservesForUser(long userId) throws ServiceException {
        serviceValidator.validation(userId);
        try{
            return reserveDAO.countReservesForUser(userId);
        }catch (DAOException e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Reserve> getReservesByUserId(long userId, int offset) throws ServiceException {
        try{
            return  convertToReserves(reserveDAO.findReservesByUserId(userId, offset));
        }catch (DAOException e){
            LOG.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    private List<Reserve> convertToReserves(List<ReserveRow> reserveRows){
        ArrayList<Reserve> reserves = new ArrayList<>();
        for (ReserveRow row : reserveRows){
            reserves.add(convertToReserve(row));
        }
        return reserves;
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

    private Reserve convertToReserve(ReserveRow reserveRow){
        Reserve reserve = null;
        if (Objects.nonNull(reserveRow.getUserId())){
            reserve = new Reserve();
            reserve.setId(reserveRow.getId());
            reserve.setProductId(reserveRow.getProductId());
            reserve.setUserId(reserveRow.getUserId());
        }
        return reserve;
    }

}
