package com.epam.repository.impl;

import com.epam.repository.ConnectionPool;
import com.epam.repository.PropertyInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class BookDAOImplTest {


    private static final String FIND_BOOK_QUERY = "SELECT * FROM products WHERE title = ? AND author = ?";
    private static final String FIND_BOOK_BY_ID = "SELECT * FROM products WHERE product_id = ? ";
    private static final String SAVE_BOOK = "INSERT INTO products (product_id,title,author,publishyear,publisher,genre,number_of_pages,is_hard_cover,description) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK = "DELETE FROM products WHERE product_id = ?";
    private static final String UPDATE_BOOK = "UPDATE products SET title = ?, author = ?,  publisher = ?, publishyear = ?, is_hard_cover = ? , number_of_pages = ?, genre = ?, description = ?  WHERE product_id = ?";
    private static final String GET_ALL_BOOKS = "SELECT * FROM products";
    private static final String COUNT_ALL = "SELECT count(product_id) FROM products";
    private static final String FIND_PAGE_FILTERED_SORTED = "SELECT * FROM products p ORDER BY p.%s %s LIMIT ? OFFSET ?";

    static PropertyInitializer propertyInitializer;
    static ConnectionPool connectionPool;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;


    @BeforeClass
    public static void startUp(){
        propertyInitializer = new PropertyInitializer();
        connectionPool = new ConnectionPoolImpl(propertyInitializer);
    }


    @Before
    public void setUp() throws Exception {
        connection = connectionPool.getConnection();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void find() {

    }

    @Test
    public void findById() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findPageByFilter() {
    }
}