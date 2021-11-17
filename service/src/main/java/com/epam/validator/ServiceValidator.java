package com.epam.validator;

import com.epam.entity.Book;
import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.entity.User;

public class ServiceValidator {

    public static boolean validation(User user) {
        return !"".equals(user.getLogin()) && !"".equals(user.getPassword());
    }

    public static boolean validation(Reserve reserve){
        return reserve.getProductId() > 0;
    }

    public static boolean validation(Order order){
        return order.getId() > 0 && !order.getProductIds().equals("");
    }

    public static boolean validation(Book book){
        return !book.getTitle().equals("") && !book.getAuthor().equals("");
    }

    public static boolean validation(long id) {
        return id > 0;
    }




}
