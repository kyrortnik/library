package com.epam.validator;

import com.epam.entity.User;

public class ServiceValidator {

    public static boolean validation(User user) {
        return !"".equals(user.getLogin()) && !"".equals(user.getPassword());
    }


    public static boolean validation(long id) {
        return id > 0;
    }

}
