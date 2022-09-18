package com.eneyeitech.workordermanagement.database;

import com.eneyeitech.requestmanagement.business.Request;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestStore extends StoreInstance {
    private static RequestStore instance;
    private ConcurrentHashMap<String, List<Request>> store = new ConcurrentHashMap<>();

    private RequestStore(){

    }

    public static StoreInstance getInstance(){
        if(instance == null){
            instance = new RequestStore();
        }
        return instance;
    }

    public ConcurrentHashMap<String, List<Request>> getStore(){
        return store;
    }
}
