package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.Reserve;
import com.epam.entity.User;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.OrderDAO;
import com.epam.repository.ReserveDAO;

import java.util.List;

public class ReserveServiceImpl implements ReserveService {

    private static final ReserveDAO reserveDAO = DAOFactory.getInstance().createReserveDAO();

    @Override
    public boolean save(Reserve reserve) throws ServiceException {
        try{
            Reserve temp = reserveDAO.getByUserAndProductId(reserve);
            if (!reserve.equals(temp)){
                return reserveDAO.save(reserve);
            }else {
                return false;
            }

        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteReservesByUserId(Long userId) {
        return reserveDAO.deleteByUserId(userId);
    }

    @Override
    public List<Reserve> getReservesForUser(Long userId) {
        return reserveDAO.getReservesForUser(userId);
    }

}
