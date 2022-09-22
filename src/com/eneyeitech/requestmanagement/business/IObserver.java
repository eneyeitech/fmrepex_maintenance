package com.eneyeitech.requestmanagement.business;

import com.eneyeitech.constant.Action;

public interface IObserver {
    public void execute(Request r, String id, Action a);
}
