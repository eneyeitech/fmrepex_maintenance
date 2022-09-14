package com.eneyeitech.usermanagement.presentation;

import com.eneyeitech.usermanagement.business.*;

import java.util.List;
import java.util.Scanner;

public class AdministratorConsole {

    private Scanner scanner;
    private UserService userService;

    public AdministratorConsole(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public User approveUser(User admin){
        if(admin.getUserType() != UserType.ADMINISTRATOR){
            System.out.println("User not authorized for this approval action");
            return null;
        }
        showPrompt("Approve a user");
        String email = getEmail("Please enter user email: ");
        User userToApprove = (User) userService.get(email);
        if (userToApprove==null){
            System.out.println("User does not exist!");
        }else{
            System.out.println(userToApprove);
            userToApprove.setApproved(true);
            System.out.println(userToApprove);
            userService.add(userToApprove);
        }
        return userToApprove;
    }

    public void listUsers(){
        showPrompt("User list!");
        List<User> list = (List<User>) userService.getAll();
        int i = 0;
        for(User user:list){
            System.out.printf("%s: %s(%s) - %s | %s.\n",++i, user.getFullName(), user.getUserType().toString(), user.getEmail(), user.isApproved());
        }
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


}
