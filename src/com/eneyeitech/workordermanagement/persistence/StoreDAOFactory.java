package com.eneyeitech.workordermanagement.persistence;

import com.eneyeitech.workordermanagement.database.RequestStore;
import com.eneyeitech.workordermanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return RequestStore.getInstance();
    }

    @Override
    public DAO getRequestDAO() {
        return new StoreRequestDAO();
    }

    @Override
    public DAO getWorkOrderDAO() {
        return new StoreWorkOrderDAO();
    }
}
