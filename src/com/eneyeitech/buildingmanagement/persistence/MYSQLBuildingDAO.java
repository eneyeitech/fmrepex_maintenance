package com.eneyeitech.buildingmanagement.persistence;

import com.eneyeitech.buildingmanagement.business.Building;
import com.eneyeitech.usermanagement.exception.TableException;

import java.util.List;

public class MYSQLBuildingDAO extends DAO<Building>{
    @Override
    public boolean add(Building building) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Building get(String id) {
        return null;
    }

    @Override
    public List<Building> getAll() {
        return null;
    }

    @Override
    public boolean update(Building building) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
