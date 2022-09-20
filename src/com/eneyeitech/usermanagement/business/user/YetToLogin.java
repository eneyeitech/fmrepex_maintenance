package com.eneyeitech.usermanagement.business.user;

import com.eneyeitech.usermanagement.business.User;

public class YetToLogin extends User {
    public YetToLogin(String username, String password){
        setEmail(username);
        setPassword(password);
    }
}
