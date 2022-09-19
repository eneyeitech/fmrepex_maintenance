package com.eneyeitech.requestmanagement.presentation;

import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.requestmanagement.business.Status;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Tenant;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RequestTenantConsole {
    private Scanner scanner;
    private UserService userService;
    private RequestService requestService;
    private User tenant;

    public RequestTenantConsole(Scanner scanner, UserService userService, RequestService requestService, User tenant){
        this.scanner = scanner;
        this.userService = userService;
        this.requestService = requestService;
        this.tenant = tenant;
    }

    public Request newRequest(){
        if(!canMakeRequest()){
            System.out.println("New request:: not authorised");
            return null;
        }
        showPrompt("Add new request");
        AuthorisedRequestBuilder requestBuilder = new AuthorisedRequestBuilder(scanner);
        Request requestToAdd = requestBuilder.getRequest();
        requestToAdd.setTenantEmail(tenant.getEmail());
        requestToAdd.setBuildingId(((Tenant)tenant).getBuildingId());
        requestToAdd.setManagerEmail(((Tenant)tenant).getManagerEmail());
        requestToAdd.setFlatLabel(((Tenant)tenant).getFlatNoOrLabel());
        boolean added = requestService.add(requestToAdd);
        if(added){
            System.out.println(requestToAdd);
            System.out.println("Request added!");
        } else {
            System.out.println("Request already exist");
        }
        return requestToAdd;

    }

    public void removeRequest(){
        Request request = getRequest();
        showPrompt("Remove a request");
        boolean removed = requestService.remove(request.getId());
        if(removed){
            System.out.println("Request removed!");
        } else {
            System.out.println("Request does not exist!");
        }
    }

    public Request getRequest(){
        if(!canMakeRequest()){
            System.out.println("Get request:: not authorised");
        }
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = requestService.get(requestId);
        if (request==null){
            System.out.println("request does not exist!");
        }else{

            if(tenant.getEmail().equalsIgnoreCase(request.getTenantEmail())){
                System.out.println(request);
            }else {
                System.out.println("Not authorized to get request");
                request = null;
            }
        }
        return request;
    }

    public void signOffRequest(){
        Request request = getRequest();
        if(request == null){
            return;
        }
        if(request.getStatus() == Status.COMPLETED) {
            request.setSignedOff(true);
        }else{
            System.out.println("Request cannot be signed off");
        }
    }

    public void listRequests(){
        showPrompt("Request list!");
        List<Request> list = requestService.getAll(tenant.getEmail());
        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getRequestId(){
        return getString("Enter request id: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public boolean isTenant(){
        if(tenant != null && tenant.getUserType() != UserType.TENANT){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }

    public boolean hasBuilding(){
        return ((Tenant)tenant).hasAnAssignedBuilding();
    }
    public boolean hasManager(){
        return ((Tenant)tenant).hasAManager();
    }
    public boolean hasFlat(){
        return ((Tenant)tenant).hasAFlat();
    }
    public boolean canMakeRequest(){
        return isTenant() && hasBuilding() && hasManager() && hasFlat();
    }
}
