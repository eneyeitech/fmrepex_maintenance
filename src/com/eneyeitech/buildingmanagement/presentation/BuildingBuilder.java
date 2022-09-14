package com.eneyeitech.buildingmanagement.presentation;

import com.eneyeitech.buildingmanagement.business.Address;
import com.eneyeitech.buildingmanagement.business.Building;
import com.eneyeitech.buildingmanagement.business.Coordinate;
import com.eneyeitech.buildingmanagement.business.ManagedBuilding;
import com.eneyeitech.buildingmanagement.helper.BuildingIdGenerator;

import java.util.Scanner;

public class BuildingBuilder {
    private Scanner scanner;
    private Building building;
    private String name;
    private AddressBuilder addressBuilder;
    private CoordinateBuilder coordinateBuilder;

    public BuildingBuilder(Scanner scanner){
        this.scanner = scanner;
        showPrompt("##::Building::##");
        name = getString("Enter building name: ");
        addressBuilder = new AddressBuilder(scanner);
        coordinateBuilder = new CoordinateBuilder(scanner);
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Building getBuilding(){
        building = new ManagedBuilding(new BuildingIdGenerator(10));
        building.setName(name);
        building.setAddress(addressBuilder.getAddress());
        building.setCoordinate(coordinateBuilder.getCoordinate());
        return  building;
    }
}
