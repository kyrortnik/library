package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.OrderDAO;
import com.epam.repository.ReserveDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReserveServiceImpl implements ReserveService {

    private static final ReserveDAO reserveDAO = DAOFactory.getInstance().createReserveDAO();

    @Override
    public boolean save(Reserve reserve) throws ServiceException {
        try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            ReserveRow foundReserveRow = reserveDAO.getByUserAndProductId(reserveRow);
            if (!reserveRow.equals(foundReserveRow)){
                return reserveDAO.save(reserveRow);
            }else {
                return false;
            }

        }catch (DAOException e){
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



    @Override
    public boolean deleteReservesByUserId(Long userId) {
        return reserveDAO.deleteByUserId(userId);
    }

    @Override
    public List<Reserve> getReservesForUser(Long userId) {
        return convertToReserves(reserveDAO.getReservesForUser(userId));
    }


    @Override
    public int countReservesForUser(long userId) {
        return reserveDAO.countReservesForUser(userId);
    }

    private Page<Reserve> convertToServicePage(Pageable<ReserveRow> reserveRowPageable) {
        Page<Reserve> page = new Page<>();
        page.setPageNumber(reserveRowPageable.getPageNumber());
        page.setLimit(reserveRowPageable.getLimit());
        page.setTotalElements(reserveRowPageable.getTotalElements());
        page.setElements(convertToReserves(reserveRowPageable.getElements()));
        page.setFilter(convertToReserve(reserveRowPageable.getFilter()));
        page.setSortBy(reserveRowPageable.getSortBy());
        page.setDirection(reserveRowPageable.getDirection());
        return page;
    }

    @Override
    public boolean productExistsInOrder(Reserve reserve) {
        Long userId = reserve.getUserId();
        Long productId = reserve.getProductId();
        ReserveRow foundRow;
        foundRow = convertToReserveRow(reserve);
         return reserveDAO.orderForReserveExists(foundRow);

    }


    @Override
    public List<Reserve> findReservationsByUserId(long userId, int row) {
        return  convertToReserves(reserveDAO.findReservationsByUserId(userId,row));
    }

    public boolean reserveForUserExists(Reserve reserve) {
        boolean flag;
       ReserveRow reserveRow = convertToReserveRow(reserve);
       ReserveRow foundRow =  reserveDAO.get(reserveRow);
       if (reserveRow.getUserId().equals(foundRow.getUserId())
               && reserveRow.getProductId().equals(foundRow.getProductId())){
           flag = true;
       }else
           flag = false;

       return flag;
    }





    private List<Reserve> convertToReserves(List<ReserveRow> reserveRows){
        ArrayList<Reserve> reserves = new ArrayList<>();
        for (ReserveRow row : reserveRows){
            reserves.add(convertToReserve(row));
        }
        return reserves;
    }

}
