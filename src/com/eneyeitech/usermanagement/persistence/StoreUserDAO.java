package com.eneyeitech.usermanagement.persistence;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.database.UserStore;
import com.eneyeitech.usermanagement.exception.TableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StoreUserDAO extends DAO<User>{

    public StoreUserDAO(){
        store = UserStore.getInstance().getStore();
    }

    @Override
    public boolean add(User user) {
        User userInStore = store.put(user.getEmail(), user);
        if(userInStore == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        User user = store.remove(id);
        if(user == null){
            return false;
        }
        return true;
    }

    @Override
    public User get(String id) {
        return store.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        for(Map.Entry<String, User> m: store.entrySet()){
            list.add(m.getValue());
        }
        Collections.sort(list, (o1, o2) -> o1.getFullName().compareTo(o2.getFullName()));
        return list;
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public void createTable() throws TableException {
        throw new TableException();
    }

    @Override
    public void dropTable() throws TableException {
        throw new TableException();
    }
}
