package com.eneyeitech.requestmanagement.persistence;

import com.eneyeitech.requestmanagement.database.RequestStore;
import com.eneyeitech.requestmanagement.database.StoreInstance;

public class StoreDAOFactory extends DAOFactory{

    public static StoreInstance createInstance(){
        return RequestStore.getInstance();
    }

    @Override
    public DAO getRequestDAO() {
        return new StoreRequestDAO();
    }
}
