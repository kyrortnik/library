package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.ReserveDAO;


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
            if (foundReserveRow == null){
                return reserveDAO.save(reserveRow);
            }else {
                return false;
            }
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean delete(Reserve reserve) throws ServiceException {
        try{
            ReserveRow reserveRow = convertToReserveRow(reserve);
            ReserveRow foundRow = reserveDAO.getByUserAndProductId(reserveRow);
            if (Objects.nonNull(foundRow)){
              return reserveDAO.delete(foundRow);
            }else {
                return true;
            }
        }catch (Exception e){
            throw new ServiceException(e);
        }

    }

   /* @Override
    public Reserve findReserveByUserAndProduct(Reserve reserve) throws ServiceException {
        return null;
    }*/

    /**
 * Functionality not yet implemented
 * */

    @Override
    public boolean deleteReservesByUserId(Long userId) throws ServiceException {
        try{
            return reserveDAO.deleteByUserId(userId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }


    @Override
    public List<Reserve> getReservesForUser(Long userId) throws ServiceException {
        try{
            return convertToReserves(reserveDAO.getReservesForUser(userId));
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }


    @Override
    public int countReservesForUser(long userId) throws ServiceException {
        try{
            return reserveDAO.countReservesForUser(userId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }


//    @Override
//    public boolean productExistsInOrder(Reserve reserve) throws ServiceException {
//       try{
//           ReserveRow foundRow;
//           foundRow = convertToReserveRow(reserve);
//           return reserveDAO.orderForReserveExists(foundRow);
//       }catch (DAOException e){
//           throw new ServiceException(e);
//       }
//
//    }


    @Override
    public List<Reserve> findReservationsByUserId(long userId, int row) throws ServiceException {
        try{
            return  convertToReserves(reserveDAO.findReserveByUserId(userId,row));
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * Do I need this?
     * */

/*    public boolean reserveForUserExists(Reserve reserve) {
        boolean flag;
       ReserveRow reserveRow = convertToReserveRow(reserve);
       ReserveRow foundRow =  reserveDAO.get(reserveRow);
       if (reserveRow.getUserId().equals(foundRow.getUserId())
               && reserveRow.getProductId().equals(foundRow.getProductId())){
           flag = true;
       }else
           flag = false;

       return flag;
    }*/


    private List<Reserve> convertToReserves(List<ReserveRow> reserveRows){
        ArrayList<Reserve> reserves = new ArrayList<>();
        for (ReserveRow row : reserveRows){
            reserves.add(convertToReserve(row));
        }
        return reserves;
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
