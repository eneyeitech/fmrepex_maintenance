package com.eneyeitech.command;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.usermanagement.business.User;

public class RequestCommand {

    private User user;
    private Request request;
    private RequestService requestService;


    public RequestCommand(User user, Request request, RequestService requestService){
        this.user = user;
        this.request = request;
        this.requestService = requestService;
    }

    public void createRequest(){
        requestService.add(request);
    }

    public void sendRequest(){

    }

    public void signOffRequest(){
        request.setSignedOff(true);
    }

    public void activateRequest(){

    }
}
