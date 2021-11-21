package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.ReserveDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.validator.ServiceValidator.validation;

public class ReserveServiceImpl implements ReserveService {

    private final ReserveDAO reserveDAO;

    private static final Logger log = Logger.getLogger(ReserveServiceImpl.class.getName());

    public ReserveServiceImpl(ReserveDAO reserveDAO) {
        this.reserveDAO = reserveDAO;
    }

    @Override
    public boolean save(Reserve reserve) throws ServiceException {
        if (validation(reserve)){
            return false;
        }try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            ReserveRow foundReserveRow = reserveDAO.getByUserAndProductId(reserveRow);
            if (Objects.isNull(foundReserveRow)){
                return reserveDAO.save(reserveRow);
            }else {
                return false;
            }
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean delete(Reserve reserve) throws ServiceException {
        if (validation(reserve)){
            return false;
        }try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            ReserveRow foundRow = reserveDAO.getByUserAndProductId(reserveRow);
            if (Objects.nonNull(foundRow)){
              return reserveDAO.delete(foundRow);
            }else {
                return true;
            }
        }catch (Exception e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public boolean deleteReservesByUserId(Long userId) throws ServiceException {
        if (!validation(userId)){
            return false;
        }try{
            return reserveDAO.deleteByUserId(userId);
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Reserve> getReservesForUser(Long userId) throws ServiceException {
       try{
            return convertToReserves(reserveDAO.getReservesForUser(userId));
        }catch (DAOException e){
           log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    @Override
    public int countReservesForUser(long userId) throws ServiceException {
        if (!validation(userId)){
            return 0;
        }try{
            return reserveDAO.countReservesForUser(userId);
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Reserve> getReservesByUserId(long userId, int offset) throws ServiceException {
        try{
            return  convertToReserves(reserveDAO.getReservesByUserId(userId, offset));
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
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
