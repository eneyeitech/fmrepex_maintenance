package com.eneyeitech.requestmanagement.presentation;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.requestmanagement.business.Status;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Dependant;
import com.eneyeitech.usermanagement.business.user.Tenant;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RequestDependantConsole {
    private Scanner scanner;
    private UserService userService;
    private RequestService requestService;
    private User dependant;

    public RequestDependantConsole(Scanner scanner, UserService userService, RequestService requestService, User dependant){
        this.scanner = scanner;
        this.userService = userService;
        this.requestService = requestService;
        this.dependant = dependant;
    }

    public Request getRequest(){
        if(!isDependant()){
            System.out.println("User not authorised");
        }
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = requestService.get(requestId);
        if (request==null){
            System.out.println("request does not exist!");
        }else{

            if(canViewRequest(request)){
                System.out.println(request);
            }else {
                System.out.println("Not authorized to get request");
                request = null;
            }
        }
        return request;
    }

    public void listRequests(){
        if(!isDependant()){
            System.out.println("User not authorised");
        }
        showPrompt("Request list!");
        List<Request> list = requestService.getAll(((Dependant)dependant).getTenantEmail());
        int i = 0;
        for(Request request:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = request.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s(%s) - %s - %s - %s | %s.\n",++i, request.getId(), request.getAsset(), request.getCategory(), request.getStatus(),request.getTenantEmail(), request.getManagerEmail(), formatDateTime);
        }
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

    public boolean isDependant(){
        if(dependant != null && dependant.getUserType() != UserType.DEPENDANT){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }


    public boolean canViewRequest(Request request){

        if(((Dependant) dependant).getTenantEmail().equalsIgnoreCase(request.getTenantEmail())){
            return true;
        }
        System.out.println("Dependant cannot view request");
        return false;
    }
}
