package com.eaibot.running.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yist
 * @date 2019/1/5
 */

public class LoginUser {

    public static List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        allUsers.add("admin,admin");
        allUsers.add("user,user");
        return allUsers;
    }

}
