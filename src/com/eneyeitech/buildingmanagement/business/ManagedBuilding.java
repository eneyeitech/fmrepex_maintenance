package com.eneyeitech.buildingmanagement.business;

import com.eneyeitech.buildingmanagement.helper.BuildingIdGenerator;

public class ManagedBuilding extends Building {
    private String managerEmail = null;

    public ManagedBuilding(BuildingIdGenerator buildingIdGenerator){
        id = buildingIdGenerator.generate();
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    @Override
    public String toString() {
        return "ManagedBuilding{" +
                "managerEmail='" + managerEmail + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", coordinate=" + coordinate +
                '}';
    }
}
