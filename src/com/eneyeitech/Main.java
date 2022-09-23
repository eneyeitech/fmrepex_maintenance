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

        // Adding admin as first entry
        User admin = UserFactory.getUser(UserType.ADMINISTRATOR);
        String e = "admin@gmail.com";
        admin.setFullName("Abdulmumin".toUpperCase(Locale.ROOT));
        admin.setEmail(e);
        admin.setPhoneNumber("08051185104");
        admin.setPassword("pxstar");

        System.out.println(admin);

        UserManagement registerManager = new Registration(admin, userService);
        new SecurityMonitor(registerManager);
        new GeneralLogger(registerManager);
        new EmailNotifier(registerManager);
        registerManager.handle();

        AuthenticationConsole authenticationConsole = new AuthenticationConsole(scanner, null);

        // Run main
        Main main = new Main();
        User loggedInUser = null;
        while (true) {

            do {
                authenticationConsole.menuDisplay();
                loggedInUser = authenticationConsole.handle();
            } while (loggedInUser == null);

            main.mainChoice(loggedInUser, main);
            loggedInUser = null;

            main.showMessage(main.exitOptions());
            int c = main.exitChoice();

            if(c==0){
                break;
            }
        }
    }

    public int mainChoice(User loggedInUser, Main main){
        switch (loggedInUser.getUserType()){

            case ADMINISTRATOR:
                AdministratorConsole administratorConsole = new AdministratorConsole(scanner, loggedInUser);
                int c = 20;
                do{
                    administratorConsole.menuDisplay();
                    c = administratorConsole.handleSelection();
                }while (c!=0);
                break;
            case TECHNICIAN:
                System.out.println(loggedInUser);
                TechnicianConsole technicianConsole = new TechnicianConsole(scanner, loggedInUser);
                int t= 23;
                do{
                    technicianConsole.menuDisplay();
                    t = technicianConsole.handleSelection();
                }while (t!= 0);
                break;
            case DEPENDANT:
                System.out.println(loggedInUser);
                DependantConsole dependantConsole = new DependantConsole(scanner, loggedInUser);
                int q= 23;
                do{
                    dependantConsole.menuDisplay();
                    q = dependantConsole.handleSelection();
                }while (q!= 0);
                break;
            case MANAGER:
                ManagerConsole managerConsole = new ManagerConsole(scanner, loggedInUser);
                int i= 23;
                do{
                    managerConsole.menuDisplay();
                    i = managerConsole.handleSelection();

                }while (i!= 0);
                break;
            case TENANT:
                System.out.println(loggedInUser);
                TenantConsole tenantConsole = new TenantConsole(scanner, loggedInUser);
                int r= 23;
                do{
                    tenantConsole.menuDisplay();
                    r = tenantConsole.handleSelection();
                }while (r!= 0);
                break;
            default:
        }
        return 9;
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
