package com.eneyeitech.buildingmanagement.presentation;

import com.eneyeitech.buildingmanagement.business.Building;
import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.buildingmanagement.business.ManagedBuilding;
import com.eneyeitech.usermanagement.business.User;

import java.util.List;
import java.util.Scanner;

public class BuildingConsole {
    private Scanner scanner;
    private BuildingService buildingService;

    public BuildingConsole(Scanner scanner, BuildingService buildingService){
        this.scanner = scanner;
        this.buildingService = buildingService;
    }

    public Building newBuilding(){
        showPrompt("Add new building");
        BuildingBuilder buildingBuilder = new BuildingBuilder(scanner);
        ManagedBuilding newBuilding = (ManagedBuilding) buildingBuilder.getBuilding();
        boolean added = buildingService.add(newBuilding);
        if(added){
            System.out.println(newBuilding);
            System.out.println("Building added!");
        } else {

            System.out.println("Building already exist");
        }
        return newBuilding;
    }

    public void removeBuilding(){
        showPrompt("Remove a building");
        String buildingId = getBuildingId();
        boolean removed = buildingService.remove(buildingId);
        if(removed){
            System.out.println("Building removed!");
        } else {
            System.out.println("Building does not exist!");
        }
    }

    public Building getBuilding(){
        showPrompt("Get a building");
        String buildingId = getBuildingId();
        ManagedBuilding building = (ManagedBuilding) buildingService.get(buildingId);
        if (building==null){
            System.out.println("building does not exist!");
        }else{
            System.out.println(building);
        }
        return building;
    }

    public void listBuildings(){
        showPrompt("Building list!");
        List<Building> list =  buildingService.getAll();
        int i = 0;
        for(Building building:list){
            System.out.printf("%s: %s(%s) - %s.\n",++i, building.getName(), building.getAddress().getState().toString(), building.getId());
        }
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getBuildingId(){
        return getString("Enter building id: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }
}
