package com.eneyeitech.usermanagement.persistence;

import com.eneyeitech.usermanagement.exception.TableException;

import java.util.List;
import java.util.Map;

public abstract class DAO<T> {
    protected Map<String, T> store;
    abstract public boolean add(T t);
    abstract public boolean remove(String id);

    abstract public T get(String id);
    abstract  public List<T> getAll();
    abstract public void clear();
    abstract public void createTable() throws TableException;
    abstract public void dropTable() throws TableException;

}
