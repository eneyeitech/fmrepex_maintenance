package com.eneyeitech.usermanagement.business;

import com.eneyeitech.usermanagement.business.user.*;

public abstract class UserFactory {
    public static User getUser(UserType userType){
        switch (userType){
            case TENANT:
                return new Tenant();
            case MANAGER:
                return new Manager();
            case DEPENDANT:
                return new Dependant();
            case TECHNICIAN:
                return new Technician();
            case ADMINISTRATOR:
                return new Administrator();
            default:
                return null;
        }
    }
}
