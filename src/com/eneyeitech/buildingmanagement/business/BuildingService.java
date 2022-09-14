package com.eneyeitech.buildingmanagement.business;

import com.eneyeitech.buildingmanagement.persistence.DAO;
import com.eneyeitech.buildingmanagement.persistence.DAOFactory;

import java.util.List;

public class BuildingService implements ICrudService<Building>{

    private DAO buildingDAO;
    private DAOFactory storeFactory;

    public BuildingService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        buildingDAO = storeFactory.getBuildingDAO();
    }

    @Override
    public boolean add(Building building) {
        return buildingDAO.add(building);
    }

    @Override
    public boolean remove(String id) {
        return buildingDAO.remove(id);
    }

    @Override
    public boolean update(Building building) {
        return buildingDAO.update(building);
    }

    @Override
    public Building get(String id) {
        return (Building) buildingDAO.get(id);
    }

    @Override
    public List<Building> getAll() {
        return buildingDAO.getAll();
    }
}
