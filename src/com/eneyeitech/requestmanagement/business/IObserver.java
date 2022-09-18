package com.eneyeitech.requestmanagement.business;

public interface IObserver {
    public void execute(Request r, String id, Action a);
}
