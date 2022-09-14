package com.eneyeitech.usermanagement.database;

import java.util.Map;

public abstract class StoreInstance<T> {
    abstract public Map<String, T> getStore();
}
