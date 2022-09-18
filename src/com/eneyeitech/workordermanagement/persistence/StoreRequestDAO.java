package com.eneyeitech.workordermanagement.persistence;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.workordermanagement.database.RequestStore;
import com.eneyeitech.workordermanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreRequestDAO extends DAO<Request> {

    public StoreRequestDAO(){
        store = RequestStore.getInstance().getStore();
    }

    public ConcurrentHashMap<String, List<Request>> convertToManagerKey(Map<String, List<Request>> storage){

        return null;
    }

    @Override
    public boolean add(Request request) {
        String managerEmail = request.getManagerEmail();
        List<Request> requests;
        List<Request> requestsInStore;
        boolean added;
        if(store.containsKey(managerEmail)){
            requests = new ArrayList<>(store.get(managerEmail));
            added = requests.add(request);
            requestsInStore = store.put(managerEmail, requests);
            return added;
        }
        requests = new ArrayList<>();
        added = requests.add(request);
        requestsInStore = store.put(managerEmail, requests);
        return added;
    }

    @Override
    public boolean remove(String id) {
        List<Request> requests;
        for(Map.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(id)){
                    String mEmail = r.getManagerEmail();
                    boolean removed = requests.remove(r);
                    if(removed){
                        store.put(mEmail, requests);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Request get(String id) {
        List<Request> requests;
        for(Map.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(id)){
                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public List<Request> getAll() {
        List<Request> list = new ArrayList<>();
        for(Map.Entry<String, List<Request>> m: store.entrySet()){
            list.addAll(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getManagerEmail().compareTo(o2.getManagerEmail()));
        return list;
    }

    @Override
    public List<Request> getAll(String managerEmail) {
        List<Request> list;
        if(store.containsKey(managerEmail)){
            list = new ArrayList<>(store.get(managerEmail));
            Collections.sort(list, (o1, o2) -> o1.getCreatedDateTime().toLocalDate().compareTo(o2.getCreatedDateTime().toLocalDate()));
            return list;
        }
        list = new ArrayList<>();
        return list;
    }

    @Override
    public boolean update(Request request) {
        String requestId = request.getId();
        List<Request> requests;
        for(Map.Entry<String, List<Request>> m: store.entrySet()){
            requests = new ArrayList<>(m.getValue());
            for(Request r: requests){
                if(r.getId().equalsIgnoreCase(requestId)){
                    String mEmail = r.getManagerEmail();
                    int index = requests.indexOf(r);
                    boolean removed = requests.remove(r);

                    if(removed && index >= 0){
                        requests.add(index, request);
                        store.put(mEmail, requests);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
