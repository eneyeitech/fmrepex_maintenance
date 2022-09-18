package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.workordermanagement.business.WORequestService;

import java.util.List;
import java.util.Scanner;

public class WORequestConsole {

    private Scanner scanner;
    private RequestService requestService;
    private WORequestService woRequestService;

    public WORequestConsole(Scanner scanner, RequestService requestService, WORequestService woRequestService){
        this.scanner = scanner;
        this.requestService = requestService;
        this.woRequestService = woRequestService;
    }


    public Request newRequest(){
        showPrompt("Add new request");
        RequestBuilder requestBuilder = new RequestBuilder(scanner);
        Request requestToAdd = requestBuilder.getRequest();
        boolean added = requestService.add(requestToAdd);

        if(added){
            System.out.println("Request added!");
        } else {
            System.out.println("Request already exist");
        }
        return requestToAdd;
    }

    public void removeRequest(){
        showPrompt("Remove a request");
        String requestId = getRequestId();
        boolean removed = requestService.remove(requestId);
        if(removed){
            System.out.println("Request removed!");
        } else {
            System.out.println("Request does not exist!");
        }
    }

    public Request getRequest(){
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = requestService.get(requestId);
        if (request==null){
            System.out.println("request does not exist!");
        }else{
            System.out.println(request);
        }
        return request;
    }

    public void listRequests(){
        showPrompt("Request list!");
        String tenantEmail = getString("Enter tenant email (leave blank to return all requests)");
        List<Request> list;
        if(tenantEmail.isBlank() || tenantEmail.isEmpty()){
            list = requestService.getAll();
        }else{
            list = requestService.getAll(tenantEmail);
        }
        int i = 0;
        for(Request request:list){
            //System.out.printf("%s: %s(%s) - %s.\n",++i, building.getName(), building.getState().toString(), building.getId());
            System.out.printf("%s: %s.\n",++i,request);
        }
    }

    public void woListRequests(){
        showPrompt("WO Request list!");
        String managerEmail = getString("Enter manager email (leave blank to return all requests)");
        List<Request> list;
        if(managerEmail.isBlank() || managerEmail.isEmpty()){
            list = woRequestService.getAll();
        }else{
            list = woRequestService.getAll(managerEmail);
        }
        int i = 0;
        for(Request request:list){
            //System.out.printf("%s: %s(%s) - %s.\n",++i, building.getName(), building.getState().toString(), building.getId());
            System.out.printf("%s: %s.\n",++i,request);
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
}
