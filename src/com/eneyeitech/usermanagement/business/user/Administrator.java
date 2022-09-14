package com.eneyeitech.usermanagement.business.user;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserType;

public class Administrator extends User {
    {
        this.setUserType(UserType.ADMINISTRATOR);
        this.setApproved(true);
    }
}
