package com.eneyeitech.requestmanagement.business;

public interface ISubject {
    public void registerObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers(Request r, String id, Action a);
}
