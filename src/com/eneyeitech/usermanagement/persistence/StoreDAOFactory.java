package com.eneyeitech.usermanagement.persistence;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.database.StoreInstance;
import com.eneyeitech.usermanagement.database.UserStore;

public class StoreDAOFactory extends DAOFactory{

    public static StoreInstance createInstance(){
        return UserStore.getInstance();
    }

    @Override
    public DAO<User> getUserDAO() {
        return new StoreUserDAO();
    }
}
