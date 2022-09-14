package com.eneyeitech.buildingmanagement.database;

import com.eneyeitech.buildingmanagement.business.Building;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BuildingStore extends com.eneyeitech.buildingmanagement.database.StoreInstance {
    private static BuildingStore instance;
    private ConcurrentHashMap<String, Building> store = new ConcurrentHashMap<>();

    private BuildingStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new BuildingStore();
        }
        return instance;
    }

    public Map<String, Building> getStore(){
        return store;
    }
}
