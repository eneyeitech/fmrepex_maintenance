package com.eneyeitech.buildingmanagement.presentation;

import com.eneyeitech.builder.BuildingBuilder;
import com.eneyeitech.buildingmanagement.business.Building;
import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.buildingmanagement.business.ManagedBuilding;
import com.eneyeitech.command.BuildingCommand;
import com.eneyeitech.command.Command;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Tenant;

import java.util.List;
import java.util.Scanner;

public class BuildingManagerConsole {
    private Scanner scanner;
    private UserService userService;
    private BuildingService buildingService;
    private User manager;

    public BuildingManagerConsole(Scanner scanner, UserService userService, BuildingService buildingService, User manager){
        this.scanner = scanner;
        this.userService = userService;
        this.buildingService = buildingService;
        this.manager = manager;
    }

    public Building addBuilding(){
        showPrompt("Add new building");
        BuildingBuilder buildingBuilder = new BuildingBuilder(scanner);
        ManagedBuilding newBuilding = (ManagedBuilding) buildingBuilder.getBuilding();
        newBuilding.setManagerEmail(manager.getEmail());
        Command command = new BuildingCommand(manager, null, newBuilding, buildingService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
        return newBuilding;
    }

    public void removeBuilding(){
        showPrompt("--Remove a building");
        Building building = getBuilding();
        String buildingId = building != null?building.getId():"";
        boolean removed = buildingService.remove(buildingId);
        if(removed){
            System.out.println("Building removed!");
        } else {
            System.out.println("Building does not exist!");
        }
    }

    public Building getBuilding(){
        if(!isManagerAndApproved()){
            return null;
        }
        showPrompt("Get a building");
        String buildingId = getBuildingId();
        ManagedBuilding building = (ManagedBuilding) buildingService.get(buildingId);
        if (building==null){
            System.out.println("building does not exist!");
        }else{
            if(addedBuilding(building)){
                return building;
            }
            System.out.println("manager not authorised to get building!");
        }
        return null;
    }

    public void listBuildings(){
        if(!isManagerAndApproved()){
            return;
        }
        showPrompt("Building list!");
        List<Building> list =  buildingService.getAll();
        int i = 0;
        for(Building building:list){
            if(addedBuilding((ManagedBuilding) building)){
              System.out.printf("%s: %s(%s) - %s.\n",++i, building.getName(), building.getState().toString(), building.getId());
            }
        }
    }


    public boolean isManager(){
        if(manager != null && manager.getUserType() != UserType.MANAGER){
            System.out.println("User not authorized to add tenant");
            return false;
        }
        return true;
    }

    public boolean addedBuilding(ManagedBuilding building){
        if(building==null){
            return false;
        }
        return (building.getManagerEmail().equalsIgnoreCase(manager.getEmail()));
    }

    public boolean isManagerAndApproved(){
        return (isManager() && isApproved());
    }

    public boolean isApproved(){
        if(manager.isApproved()){
            return true;
        }else{
            System.out.println("Manager not approved");
            return false;
        }
    }
    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getBuildingId(){
        return getString("Enter building id: ");
    }
    private String getTenantEmail(){
        return getString("Enter tenant email: ");
    }

    private String getFlatNoOrLabel(){
        return getString("Enter flat no or label: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public void assignTenant(){
        Building building = getBuilding();
        String flatLabel = getFlatNoOrLabel();
        String tenantEmail = getTenantEmail();
        User tenant = (User) userService.get(tenantEmail);

        if(tenant != null && building != null && tenant.getUserType()==UserType.TENANT){
            ((Tenant)tenant).setFlatNoOrLabel(flatLabel);
            Command command = new BuildingCommand(manager, tenant,building, buildingService);
            new com.eneyeitech.command.EmailNotifier(command);
            command.actionRequester();
        } else {
            showPrompt("Error assigning tenant");
        }

    }

    public boolean isTenant(User tenant){
        if(tenant != null && tenant.getUserType() != UserType.TENANT){
            System.out.println("User not a tenant or does not exist");
            return false;
        }
        return true;
    }

    public boolean addedTenant(User tenant){
        if(manager.getEmail() == ((Tenant) tenant).getManagerEmail()){
            return true;
        }
        System.out.println("Tenant not added by manager");
        return false;
    }

    public boolean hasNoBuilding(User tenant){
        if(((Tenant)tenant).hasAnAssignedBuilding()){
            System.out.println("Tenant has been assigned a building");
            return false;
        }

        return true;
    }

    public boolean tenantCanBeAssignedToBuilding(User tenant){
        return (addedTenant(tenant) && hasNoBuilding(tenant));
    }
    public void deAssignTenant(){
        Building building = getBuilding();
        if(building == null){
            System.out.println("Unauthorized action");
            return;
        }
        String tenantEmail = getTenantEmail();
        User tenant = (User) userService.get(tenantEmail);
        if(tenant == null){
            System.out.println("Tenant does not exist");
            return;
        }
        boolean assigned = buildingService.deAssignTenantToBuilding(building, tenant);
        if(assigned){
            ((Tenant) tenant).setBuildingId(null);
            ((Tenant) tenant).setFlatNoOrLabel(null);
            userService.update(tenant);
            buildingService.update(building);
            System.out.println("Tenant de assigned to building!");
        } else {
            System.out.println("Error occurred de assigning tenant");
        }
    }

    public void listBuildingOccupants(){
        String buildingId = getBuildingId();
        List<Tenant> list = buildingService.getOccupants(buildingId);
        if(list == null || list.size() == 0){
            System.out.println("No occupants");
            return;
        }
        int i = 0;
        showPrompt("List of building occupants!");
        for(Tenant tenant: list){
            System.out.printf("%s: %s(%s) - %s|%s[%s].\n",++i, tenant.getFullName(), tenant.getEmail(), tenant.getBuildingId(),tenant.getManagerEmail(),tenant.getFlatNoOrLabel());
        }
    }
}
