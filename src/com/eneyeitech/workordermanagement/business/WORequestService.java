package com.eneyeitech.workordermanagement.business;


import com.eneyeitech.requestmanagement.business.Action;
import com.eneyeitech.requestmanagement.business.IObserver;
import com.eneyeitech.requestmanagement.business.ISubject;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.workordermanagement.persistence.DAO;
import com.eneyeitech.workordermanagement.persistence.DAOFactory;

import java.util.List;

public class WORequestService implements ICrudService<Request>, IObserver {
    private ISubject requestService;
    private DAO requestDAO;
    private DAOFactory storeFactory;

    public WORequestService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
    }
    public WORequestService(ISubject requestService){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
        this.requestService=requestService;
        this.requestService.registerObserver(this);
    }


    @Override
    public boolean add(Request request) {
        return requestDAO.add(request);
    }

    @Override
    public boolean remove(String id) {
        return requestDAO.remove(id);
    }

    @Override
    public boolean update(Request request) {
        return requestDAO.update(request);
    }

    @Override
    public Request get(String id) {
        return (Request) requestDAO.get(id);
    }

    @Override
    public List<Request> getAll() {
        return requestDAO.getAll();
    }

    @Override
    public List<Request> getAll(String tenantEmail) {
        return requestDAO.getAll(tenantEmail);
    }

    @Override
    public void execute(Request r, String id, Action a) {
        switch (a){
            case ADD:
                System.out.println("ADD");
                add(r);
                break;
            case REMOVE:
                remove(id);
                System.out.println("REMOVE");
                break;
            case UPDATE:
                System.out.println("UPDATE");
                break;
            default:
        }
    }
}
