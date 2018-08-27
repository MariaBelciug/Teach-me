package com.assist.internship.helpers;

import com.assist.internship.model.Role;
import com.assist.internship.model.User;

import java.util.Objects;

public class RoleType {

    public static Boolean isAdmin(User user){

        Boolean control = false;

        for(Role role: user.getRoles())
        {
             control = Objects.equals(role.getName(), "ADMIN");
        }

        return  control;
    }

}
