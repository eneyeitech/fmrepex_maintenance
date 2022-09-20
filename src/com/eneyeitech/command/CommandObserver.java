package com.eneyeitech.command;

import com.eneyeitech.Observable;
import com.eneyeitech.Observer;
import com.eneyeitech.authentication.UserManagement;

abstract public class CommandObserver implements Observer {
    private Command command;

    public CommandObserver(Command command){
        this.command = command;
        this.command.attach(this);
    }
    @Override
    public void update(Observable observable) {
        if(observable.equals(command)){
            doUpdate(command);
        }
    }

    abstract public void doUpdate(Command command);
}
