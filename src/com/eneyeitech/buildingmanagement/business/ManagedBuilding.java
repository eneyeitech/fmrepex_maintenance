package com.eneyeitech.buildingmanagement.business;

public class ManagedBuilding extends AbstractBuilding{
    private String managerEmail = null;

    public ManagedBuilding(){

    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
}
