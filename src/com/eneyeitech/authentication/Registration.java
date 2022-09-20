package com.eneyeitech.authentication;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;

public class Registration extends UserManagement {
    private UserService userService;

    public Registration(User userToRegister, UserService userService){
        super(userToRegister);
        this.userService = userService;
    }

    private boolean register(){
        return userService.add(getUser());
    }

    @Override
    public User handle() {
        if(register()){
            setSuccessful(true);
        }else{
            setSuccessful(false);
        }
        notifyObservers();
        return getUser();
    }
}
