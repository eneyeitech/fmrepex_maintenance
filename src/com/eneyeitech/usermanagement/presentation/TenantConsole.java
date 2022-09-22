package com.eneyeitech.usermanagement.presentation;

import com.eneyeitech.builder.UserBuilder;
import com.eneyeitech.command.Command;
import com.eneyeitech.command.UserCommand;
import com.eneyeitech.usermanagement.business.*;
import com.eneyeitech.usermanagement.business.user.Tenant;

import java.util.List;
import java.util.Scanner;

public class TenantConsole {

    private Scanner scanner;
    private UserService userService;

    private User tenant;

    public TenantConsole(Scanner scanner, UserService userService, User tenant){
        this.scanner = scanner;
        this.userService = userService;
        this.tenant = tenant;
    }

    public boolean isTenant(){
        if(tenant != null && tenant.getUserType() != UserType.TENANT){
            System.out.println("User not authorized to add tenant");
            return false;
        }
        return true;
    }
    public boolean isApproved(){
        return tenant.isApproved();
    }
    public void addDependant(){
        showPrompt("Add Dependant");
        String type = UserType.DEPENDANT.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        Command command = new UserCommand(tenant, newUser, userService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void removeDependant(){
        showPrompt("Remove a user");
        String email = getEmail("Please enter user email: ");
        User dependant = (User) userService.get(email);
        boolean removed = userService.removeDependantFromTenant(tenant, dependant);
        if(removed){
            removed = userService.remove(email);
            System.out.println("User removed!");
            userService.add(tenant);
        } else {
            System.out.println("User does not exist!");
        }
    }

    public void listDependants(){
        boolean isTenant = isTenant();
        if(!isTenant){
            return;
        }
        showPrompt("Dependant list!");
        //List<User> list = (List<User>) userService.getAll();
        List<User> list = ((Tenant) tenant).getDependantsList();
        int i = 0;
        for(User user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
    }

    public void tenantDetails(){
        String detail = "Name: %s\n" +
                "Email: %s\n" +
                "Phone number: %s\n" +
                "Type: %s\n" +
                "Approved: %s\n" +
                "";
        System.out.printf(detail, tenant.getFullName(), tenant.getEmail(), tenant.getPhoneNumber(), tenant.getUserType().toString(),tenant.isApproved());
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
