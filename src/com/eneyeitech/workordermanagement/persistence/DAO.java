package com.eneyeitech.workordermanagement.persistence;



import com.eneyeitech.requestmanagement.helper.RequestIdGenerator;
import com.eneyeitech.workordermanagement.exception.TableException;
import com.eneyeitech.workordermanagement.helper.WorkOrderIdGenerator;

import java.util.List;
import java.util.Map;

public abstract class DAO<T> {

    protected RequestIdGenerator requestIdGenerator;
    protected WorkOrderIdGenerator workOrderIdGenerator;

    public DAO(){
        requestIdGenerator = new RequestIdGenerator(10);
        workOrderIdGenerator = new WorkOrderIdGenerator(10);
    }

    protected Map<String, List<T>> store;
    abstract public boolean add(T t);
    abstract public boolean remove(String id);

    abstract public T get(String id);
    abstract  public List<T> getAll();
    abstract  public List<T> getAll(String email);
    abstract public boolean update(T t);
    abstract public void clear();
    abstract public void createTable() throws TableException;
    abstract public void dropTable() throws TableException;

}
