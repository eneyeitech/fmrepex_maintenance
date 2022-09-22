package com.eneyeitech.console;

import com.eneyeitech.builder.AuthorisedRequestBuilder;
import com.eneyeitech.builder.RequestBuilder;
import com.eneyeitech.command.Command;
import com.eneyeitech.command.RequestCommand;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Dependant;
import com.eneyeitech.workordermanagement.business.WORequestService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class DependantConsole extends Console{
    private RequestService requestService;
    private WORequestService woRequestService;
    private AuthorisedRequestBuilder requestBuilder;
    public DependantConsole(Scanner scanner, User user){
        super(scanner, user);
        requestService = new RequestService();
        woRequestService = new WORequestService();
        requestBuilder = new AuthorisedRequestBuilder(requestService, woRequestService);
    }

    public String menu(){
        return "" +
                "1. View requests\n" +
                "2. View a request\n" +
                "3. Sign off request\n" +
                "0. Back\n" +
                "";
    }
    @Override
    public void menuDisplay() {
        System.out.println(menu());
    }

    @Override
    public int handleSelection() {
        int selection = getSelectedNumber();
        switch (selection){
            case 1:
                listRequests();
                return 1;
            case 2:
                getRequest();
                return 1;
            case 3:
                signOffRequest();
                return 2;
            case 0:
                return 0;
            default:
                return 99;
        }
    }

    public Request getRequest(){
        if(!isDependant()){
            System.out.println("User not authorised");
        }
        showPrompt("Get a request");
        String requestId = getRequestId();
        Request request = requestBuilder.queryRequest(requestId);
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
        List<Request> list = requestBuilder.queryTenantRequests(((Dependant)user).getTenantEmail());
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
        Command command = new RequestCommand(user, request, requestService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
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
        if(user != null && user.getUserType() != UserType.DEPENDANT){
            System.out.println("User not authorized");
            return false;
        }
        return true;
    }


    public boolean canViewRequest(Request request){

        if(((Dependant) user).getTenantEmail().equalsIgnoreCase(request.getTenantEmail())){
            return true;
        }
        System.out.println("Dependant cannot view request");
        return false;
    }
}
