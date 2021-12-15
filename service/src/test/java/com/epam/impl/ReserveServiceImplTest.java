package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.Reserve;
import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.ReserveDAO;
import com.epam.validator.ServiceValidator;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReserveServiceImplTest {

    //mock
    private final ReserveDAO reserveDAO = Mockito.mock(ReserveDAO.class);
    private final ServiceValidator serviceValidator = ServiceValidator.getInstance();

    //testing class
    private final ReserveService reserveService = new ReserveServiceImpl(reserveDAO, serviceValidator);

    //parameters
    long id = 1L;
    long userId = 2L;
    long productId = 3L;


    @Test
    public void testSave_positive() throws DAOException, ServiceException {
        ReserveRow reserveRow = new ReserveRow(id, userId, productId);
        Reserve reserve = new Reserve(id, userId, productId);

        when(reserveDAO.save(reserveRow)).thenReturn(true);

        boolean result = reserveService.save(reserve);

        verify(reserveDAO).save(reserveRow);
        assertTrue(result);

    }

    @Test
    public void testSave_ServiceException() throws DAOException {
        ReserveRow reserveRow = new ReserveRow(id, userId, productId);
        Reserve reserve = new Reserve(id, userId, productId);
        DAOException daoException = new DAOException("testing message");

        when(reserveDAO.save(reserveRow)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            reserveService.save(reserve);
        } catch (ServiceException e) {
            actualException = e;
        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }


    @Test
    public void testDelete_positive() throws DAOException, ServiceException {
        when(reserveDAO.deleteByUserAndProduct(id, productId)).thenReturn(true);

        boolean result = reserveService.delete(id, productId);

        verify(reserveDAO).deleteByUserAndProduct(id, productId);
        assertTrue(result);

    }

    @Test
    public void testDelete_ServiceException() throws DAOException {
        DAOException daoException = new DAOException("testing message");

        when(reserveDAO.deleteByUserAndProduct(id, productId)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            reserveService.delete(id, productId);
        } catch (ServiceException e) {
            actualException = e;
        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }

}