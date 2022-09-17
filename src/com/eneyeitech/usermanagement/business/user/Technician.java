package com.eneyeitech.usermanagement.business.user;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserType;

public class Technician extends User {

    private String managerEmail = null;

    {
        this.setUserType(UserType.TECHNICIAN);
        this.setApproved(true);
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }


    public boolean hasAManager(){
        return managerEmail != null;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "managerEmail='" + managerEmail + '\'' +
                '}';
    }
}
