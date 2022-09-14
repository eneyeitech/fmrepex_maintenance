package com.eneyeitech.buildingmanagement.persistence;

import com.eneyeitech.buildingmanagement.business.Building;
import com.eneyeitech.buildingmanagement.database.BuildingStore;
import com.eneyeitech.buildingmanagement.database.StoreInstance;


public class StoreDAOFactory extends DAOFactory {

    public static StoreInstance createInstance(){
        return BuildingStore.getInstance();
    }

    @Override
    public DAO<Building> getBuildingDAO() {
        return new StoreBuildingDAO();
    }
}
