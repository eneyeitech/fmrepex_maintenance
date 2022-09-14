package com.eneyeitech.usermanagement.business.user;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserType;

public class Dependant extends User {

    private String tenantEmail = null;

    {
        this.setUserType(UserType.DEPENDANT);
        this.setApproved(true);
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    @Override
    public String toString() {
        return "Dependant{" +
                "tenantEmail='" + tenantEmail + '\'' +
                '}';
    }
}
