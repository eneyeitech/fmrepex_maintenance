package com.eneyeitech.usermanagement.presentation;

import com.eneyeitech.usermanagement.business.*;

import java.util.Scanner;

public class AuthenticationConsole {

    private Scanner scanner;
    private UserService userService;

    public AuthenticationConsole(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public void managerSignup(){
        showPrompt("Manager Sign-up");
        String type = UserType.MANAGER.toString();
        UserBuilder userBuilder = new UserBuilder(scanner);
        User newUser = userBuilder.getUser(type);
        boolean added = userService.add(newUser);
        if(added){
            System.out.println("Manager added!");
        } else {
            System.out.println("User exist");
        }
    }


    public User loginUser(){
        showPrompt("User login!");
        String email = getEmail("Enter email: ");
        String password = getPassword("Enter password: ");

        AuthenticationService authenticationService = new AuthenticationService(userService);
        User user = authenticationService.login(email, password);

        if(user == null){
            System.out.println("Credentials not available");
        }else{
            System.out.println("Login successful!");
        }
        return user;
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
