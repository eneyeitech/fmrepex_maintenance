package com.eneyeitech.workordermanagement.database;

import com.eneyeitech.requestmanagement.business.Request;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class StoreInstance<T> {
    abstract public ConcurrentHashMap<String, T> getStore();
    abstract public void updateStore(ConcurrentHashMap<String, List<T>> cm);
}
