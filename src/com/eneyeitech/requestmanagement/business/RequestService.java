package com.eneyeitech.requestmanagement.business;

import com.eneyeitech.requestmanagement.persistence.DAO;
import com.eneyeitech.requestmanagement.persistence.DAOFactory;

import java.util.List;

public class RequestService implements ICrudService<Request>{

    private DAO requestDAO;
    private DAOFactory storeFactory;

    public RequestService(){
        storeFactory = DAOFactory.getDAOFactory(DAOFactory.STORE);
        requestDAO = storeFactory.getRequestDAO();
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
}
