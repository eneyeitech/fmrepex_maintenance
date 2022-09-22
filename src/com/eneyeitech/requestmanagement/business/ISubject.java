package com.eneyeitech.requestmanagement.business;

import com.eneyeitech.constant.Action;

public interface ISubject {
    public void registerObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers(Request r, String id, Action a);
}
