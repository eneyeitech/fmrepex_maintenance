package com.eneyeitech.usermanagement.presentation;

import com.eneyeitech.usermanagement.business.*;

import java.util.List;
import java.util.Scanner;

public class UserConsole {

    private Scanner scanner;
    private UserService userService;

    public UserConsole(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public void newUser(){
        showPrompt("Add new user");

        UserBuilder userBuilder = new UserBuilder(scanner, null);
        User newUser = userBuilder.getUser();

        boolean added = userService.add(newUser);
        if(added){
            System.out.println("User added!");
        } else {
            System.out.println("User already exist");
        }
    }

    public void removeUser(){
        showPrompt("Remove a user");
        String email = getEmail("Please enter user email: ");
        boolean removed = userService.remove(email);
        if(removed){
            System.out.println("User removed!");
        } else {
            System.out.println("User does not exist!");
        }
    }

    public User getUser(){
        showPrompt("Get a user");
        String email = getEmail("Please enter user email: ");
        User user = (User) userService.get(email);
        if (user==null){
            System.out.println("User does not exist!");
        }else{
            System.out.println(user);
        }
        return user;
    }

    public void listUsers(){
        showPrompt("User list!");
        List<User> list = (List<User>) userService.getAll();
        int i = 0;
        for(User user:list){
            System.out.printf("%s: %s(%s) - %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail());
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
