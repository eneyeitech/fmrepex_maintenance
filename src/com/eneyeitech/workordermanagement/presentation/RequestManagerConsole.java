package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Tenant;
import com.eneyeitech.workordermanagement.business.WORequestService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RequestManagerConsole {
    private Scanner scanner;
    private UserService userService;
    private RequestService tenantRequestService;
    private WORequestService managementRequestService;
    private User manager;

    public RequestManagerConsole(Scanner scanner, UserService userService, RequestService tenantRequestService, WORequestService managementRequestService, User manager){
        this.scanner = scanner;
        this.userService = userService;
        this.tenantRequestService = tenantRequestService;
        this.managementRequestService = managementRequestService;
        this.manager = manager;
    }

    public Request getRequest(){
        if(!isManager()){
            System.out.println("Get request:: not authorised");
            return null;
        }
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = tenantRequestService.get(requestId);
        if (request==null){
            System.out.println("request does not exist!");
        }else{

            if(manager.getEmail().equalsIgnoreCase(request.getManagerEmail())){
                System.out.println(request);
            }else {
                System.out.println("Not authorized to get request");
                request = null;
            }
        }
        return request;
    }


    public void listTenantRequests(){
        if(!isManager()){
            System.out.println("List request:: not authorised");
        }
        showPrompt("Tenant request list!");
        String tenantEmail = getString("Enter tenant email: ");
        Tenant tenant = (Tenant) userService.get(tenantEmail);
        if(tenant == null){
            System.out.println("Tenant not found!");
            return;
        }
        if(!validTenantAndManagedByManager(tenant)){
            System.out.println("Not authorized!");
            return;
        }
        List<Request> list;
        list = tenantRequestService.getAll(tenantEmail);

        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
    }

    public void listManagerRequestsView(){
        if(!isManager()){
            System.out.println("List request:: not authorised");
        }
        showPrompt("All request list");
        List<Request> list;
        list = managementRequestService.getAll(manager.getEmail());

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

    public boolean isTenant(Tenant tenant){
        if(tenant != null && tenant.getUserType() != UserType.TENANT){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }

    public boolean hasBuilding(Tenant tenant){
        return tenant.hasAnAssignedBuilding();
    }
    public boolean hasManager(Tenant tenant){
        return tenant.hasAManager();
    }
    public boolean hasFlat(Tenant tenant){
        return tenant.hasAFlat();
    }
    public boolean canMakeRequest(Tenant tenant){
        return isTenant(tenant) && hasBuilding(tenant) && hasManager(tenant) && hasFlat(tenant);
    }

    public boolean isManager(){
        if(manager != null && manager.getUserType() != UserType.MANAGER){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }

    public boolean managedBy(Tenant tenant){
        return (manager.getEmail().equalsIgnoreCase(tenant.getManagerEmail()));
    }

    public boolean validTenantAndManagedByManager(Tenant tenant){
        return (canMakeRequest(tenant) && managedBy(tenant));
    }
}
