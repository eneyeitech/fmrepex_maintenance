package com.eneyeitech.command;

import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.user.Dependant;
import com.eneyeitech.usermanagement.business.user.Technician;
import com.eneyeitech.usermanagement.business.user.Tenant;

public class UserCommand extends Command{
    protected User loggedInUser;
    protected User userToRegister;
    private UserService userService;
    private boolean added;

    public UserCommand(User loggedInUser, User userToRegister, UserService userService){
        this.loggedInUser=loggedInUser;
        this.userToRegister = userToRegister;
        this.userService = userService;
        added = false;
    }

    public void handleRegister(){
        switch (loggedInUser.getUserType()){
            case MANAGER:
                switch (userToRegister.getUserType()){
                    case TENANT:
                        registerTenant();
                        break;
                    case TECHNICIAN:
                        registerTechnician();
                        break;
                    default:
                }
                break;
            case TENANT:
                switch (userToRegister.getUserType()){
                    case DEPENDANT:
                        registerDependant();
                        break;
                    default:
                }
                break;
            case DEPENDANT:
            case TECHNICIAN:
            case ADMINISTRATOR:
            default:
        }
    }

    private void registerTenant(){
        ((Tenant) userToRegister).setManagerEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addTenantToManager(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    private void registerTechnician(){
        ((Technician) userToRegister).setManagerEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addTechnicianToManager(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    private void registerDependant(){
        ((Dependant) userToRegister).setTenantEmail(loggedInUser.getEmail());
        if(userService.add(userToRegister)){
            if(userService.addDependantToTenant(loggedInUser, userToRegister)){
                setAdded(true);
                setSuccessful(true);
            }
        }
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public void actionRequester() {
        handleRegister();
        notifyObservers();
    }
}
