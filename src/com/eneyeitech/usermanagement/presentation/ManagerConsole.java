package com.eneyeitech.usermanagement.presentation;

import com.eneyeitech.builder.UserBuilder;
import com.eneyeitech.command.Command;
import com.eneyeitech.command.UserCommand;
import com.eneyeitech.usermanagement.business.*;
import com.eneyeitech.usermanagement.business.user.Manager;
import com.eneyeitech.usermanagement.business.user.Technician;
import com.eneyeitech.usermanagement.business.user.Tenant;

import java.util.List;
import java.util.Scanner;

public class ManagerConsole {

    private Scanner scanner;
    private UserService userService;

    private User manager;

    public ManagerConsole(Scanner scanner, UserService userService, User manager){
        this.scanner = scanner;
        this.userService = userService;
        this.manager = manager;
    }

    public boolean isManager(){
        if(manager != null && manager.getUserType() != UserType.MANAGER){
            System.out.println("User not authorized to add tenant");
            return false;
        }
        return true;
    }
    public boolean isApproved(){
        return manager.isApproved();
    }
    public void addTenant(){
        showPrompt("Add Tenant");
        String type = UserType.TENANT.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(manager, newUser, userService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void removeTenant(){
        showPrompt("Remove a user");
        String email = getEmail("Please enter user email: ");
        User tenant = (User) userService.get(email);
        boolean removed = userService.removeTenantFromManager(manager, tenant);
        if(removed){
            removed = userService.remove(email);
            System.out.println("User removed!");
            userService.add(manager);
        } else {
            System.out.println("User does not exist!");
        }
    }

    public void listTenants(){
        boolean isManager = isManager();
        if(!isManager){
            return;
        }
        showPrompt("Tenant list!");
        //List<User> list = (List<User>) userService.getAll();
        List<Tenant> list = ((Manager) manager).getTenantsList();

        int i = 0;
        for(Tenant tenant:list){
            System.out.printf("%s: %s(%s) - %s|%s[%s].\n",++i, tenant.getFullName(), tenant.getEmail(), tenant.getBuildingId(),tenant.getManagerEmail(),tenant.getFlatNoOrLabel());
        }
    }

    public void addTechnician(){
        showPrompt("Add Technician");
        String type = UserType.TECHNICIAN.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(manager, newUser, userService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void removeTechnician(){
        showPrompt("Remove a technician");
        String email = getEmail("Please enter user email: ");
        User technician = (User) userService.get(email);
        boolean removed = userService.removeTechnicianFromManager(manager, technician);
        if(removed){
            removed = userService.remove(email);
            System.out.println("Technician removed!");
            userService.add(manager);
        } else {
            System.out.println("Technician does not exist!");
        }
    }

    public void listTechnicians(){
        boolean isManager = isManager();
        if(!isManager){
            return;
        }
        showPrompt("Technician list!");
        //List<User> list = (List<User>) userService.getAll();
        List<Technician> list = ((Manager) manager).getTechniciansList();
        int i = 0;
        for(Technician user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
    }

    public void managerDetails(){
        String detail = "Name: %s\n" +
                "Email: %s\n" +
                "Phone number: %s\n" +
                "Type: %s\n" +
                "Approved: %s\n" +
                "";
        System.out.printf(detail, manager.getFullName(), manager.getEmail(), manager.getPhoneNumber(), manager.getUserType().toString(),manager.isApproved());
    }
    private void showPrompt(String prompt){
        System.out.println(prompt);
    }
    private String getEmail(String msg){
        showPrompt(msg);
        String email = scanner.nextLine();
        return email;
    }

    private String getPhoneNumber(String msg){
        showPrompt(msg);
        String phone = scanner.nextLine();
        return phone;
    }

    private String getFullName(String msg){
        showPrompt(msg);
        String name = scanner.nextLine();
        return name;
    }

    private String getPassword(String msg){
        showPrompt(msg);
        String password = scanner.nextLine();
        return password;
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }
    private String getType(){
        showPrompt("Enter one of the following admin|tenant|dependant|manager|technician");
        String type = scanner.nextLine();
        switch (type){
            case "admin":
                return UserType.ADMINISTRATOR.name();
            case "tenant":
                return UserType.TENANT.name();
            case "dependant":
                return UserType.DEPENDANT.name();
            case "manager":
                return UserType.MANAGER.name();
            case "technician":
                return UserType.TECHNICIAN.name();
            default:
                return "";
        }
    }
}
