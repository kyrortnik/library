package com.epam.impl;

import com.epam.ReserveService;
import com.epam.entity.Reserve;
import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.ReserveDAO;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ReserveServiceImplTest {

    //mock
    private final ReserveDAO reserveDAO = Mockito.mock(ReserveDAO.class);

    //testing class
    private final ReserveService reserveService = new ReserveServiceImpl(reserveDAO);

    //parameters
    private final Reserve reserve = new Reserve(1L,1L,1L);
    private final ReserveRow reserveRow = new ReserveRow(1L,1L,1L);
    //captors
    @Test
    public void testSave_positive() throws DAOException, ServiceException {

        when(reserveDAO.getByUserAndProductId(reserveRow)).thenReturn(null);
        when(reserveDAO.save(reserveRow)).thenReturn(true);
        assertTrue(reserveService.save(reserve));
    }

    @Test
    public void testDelete_positive() throws DAOException,ServiceException{

        when(reserveDAO.getByUserAndProductId(reserveRow)).thenReturn(reserveRow);
        when(reserveDAO.delete(reserveRow)).thenReturn(true);
        assertTrue(reserveService.delete(reserve));
    }

    @Test
    public void testDeleteReservesByUserId_positive() throws DAOException, ServiceException {

        when(reserveDAO.deleteByUserId(reserve.getUserId())).thenReturn(true);
        assertTrue(reserveService.deleteReservesByUserId(reserve.getUserId()));

    }
    @Test
    public void testGetReservesForUser_positive() throws DAOException, ServiceException {
        Long userId = 1L;
        List<Reserve> reserves = new ArrayList<>();
        reserves.add(new Reserve(1L,1L,1L));
        reserves.add(new Reserve(2L,1L,2L));
        reserves.add(new Reserve(3L,1L,3L));

        List<ReserveRow> reserveRows = new ArrayList<>();
        reserveRows.add(new ReserveRow(1L,1L,1L));
        reserveRows.add(new ReserveRow(2L,1L,2L));
        reserveRows.add(new ReserveRow(3L,1L,3L));

        when(reserveDAO.getReservesForUser(userId)).thenReturn(reserveRows);
        assertEquals(reserves,reserveService.getReservesForUser(userId));

    }
    @Test
    public void testCountReservesForUser_positive() throws DAOException,ServiceException{
        long userId = 1L;
        int numberOfReserves = 5;
        when(reserveDAO.countReservesForUser(userId)).thenReturn(numberOfReserves);
        assertEquals(numberOfReserves,reserveService.countReservesForUser(userId));

    }
    @Test
    public void testFindReservationsByUserId_positive() throws DAOException, ServiceException {
        long userId = 1L;
        int offset = 0;
        List<ReserveRow> reserveRows = new ArrayList<>();
        reserveRows.add(new ReserveRow(1L,1L,1L));
        reserveRows.add(new ReserveRow(2L,1L,2L));
        reserveRows.add(new ReserveRow(3L,1L,3L));

        List<Reserve> reserves = new ArrayList<>();
        reserves.add(new Reserve(1L,1L,1L));
        reserves.add(new Reserve(2L,1L,2L));
        reserves.add(new Reserve(3L,1L,3L));

        when(reserveDAO.getReservesByUserId(userId,offset)).thenReturn(reserveRows);
        assertEquals(reserves,reserveService.getReservesByUserId(userId,offset));

    }

}