package com.eneyeitech;

import com.eneyeitech.authentication.*;
import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.console.*;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.usermanagement.business.*;
import com.eneyeitech.workordermanagement.business.WORequestService;
import com.eneyeitech.workordermanagement.business.WorkOrderService;

import java.util.*;


public class Main {
    private static UserService userService;
    private static  BuildingService buildingService;
    private static RequestService requestService;
    private static WORequestService woRequestService;
    private static WorkOrderService workOrderService;
    private static Scanner scanner;
    public static void main(String[] args){
        System.out.println("FMRepEx!");

        scanner = new Scanner(System.in);
        userService = new UserService();
        buildingService = new BuildingService();
        requestService = new RequestService();
        woRequestService = new WORequestService(requestService);
        workOrderService = new WorkOrderService();

        Main main = new Main();

        // Adding admin as first entry
        User admin = main.addAdmin("Abdulmumin","admin@gmail.com", "pxstar", "08051185104");

        // Run App
        main.init(main);
    }

    public void init(Main main){
        User loggedInUser = null;
        AuthenticationConsole authenticationConsole = new AuthenticationConsole(scanner, null);
        while (true) {

            do {
                authenticationConsole.menuDisplay();
                loggedInUser = authenticationConsole.handle();
            } while (loggedInUser == null);

            main.runFMRepEx(loggedInUser);
            loggedInUser = null;

            main.showMessage(main.exitOptions());
            int c = main.exitChoice();

            if(c==0){
                break;
            }
        }
    }

    public User addAdmin(String name, String email, String password, String phoneNumber){
        User admin = UserFactory.getUser(UserType.ADMINISTRATOR);

        admin.setFullName(name.toUpperCase(Locale.ROOT));
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setPassword(password);

        UserManagement registerManager = new Registration(admin, userService);
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        return registerManager.handle();
    }

    public void runFMRepEx(User loggedInUser){
        ConsoleManager consoleManager = new ConsoleManager(scanner, loggedInUser);
        consoleManager.runMenu();
    }

    public String exitOptions(){
        return "" +
                "1. Continue\n" +
                "0. Exit\n" +
                "";
    }

    public void showMessage(String msg){
        System.out.println(msg);
    }

    public int exitChoice(){
        int selection = getNumber();
        switch (selection){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
                return 2;
        }
    }

    public int getNumber(){
        Scanner scanner = new Scanner(System.in);
        int c = 99;
        try{
            c = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Enter a number");
        }catch (NoSuchElementException e){
            System.out.println("Enter a number");
        }

        return c;
    }
}
