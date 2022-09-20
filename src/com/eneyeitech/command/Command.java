package com.eneyeitech.command;

import com.eneyeitech.Observable;
import com.eneyeitech.Observer;

import java.util.ArrayList;
import java.util.List;

abstract public class Command implements Observable {
    private List<Observer> observers;
    private boolean successful;

    public Command(){
        successful = false;
        observers = new ArrayList<>();
    }

    abstract public void actionRequester();

    protected boolean isSuccessful() {
        return successful;
    }

    protected void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public void attach(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer: observers){
            observer.update(this);
        }
    }
}
