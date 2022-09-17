package com.eneyeitech;

import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.buildingmanagement.helper.BuildingIdGenerator;
import com.eneyeitech.buildingmanagement.presentation.BuildingConsole;
import com.eneyeitech.buildingmanagement.presentation.BuildingManagerConsole;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.requestmanagement.presentation.RequestConsole;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserFactory;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.presentation.*;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class Main {
    private static UserService userService;
    private static  BuildingService buildingService;
    private static RequestService requestService;
    private static Scanner scanner;
    public static void main(String[] args){
        System.out.println("FMRepEx!");
        userService = new UserService();
        scanner = new Scanner(System.in);

        UserConsole userConsole = new UserConsole(scanner, userService);
        AuthenticationConsole authenticationConsole = new AuthenticationConsole(scanner, userService);
        buildingService = new BuildingService();
        requestService = new RequestService();
        // Adding admin as first entry
        User admin = UserFactory.getUser(UserType.ADMINISTRATOR);
        String e = "admin@gmail.com";
        admin.setFullName("Abdulmumin".toUpperCase(Locale.ROOT));
        admin.setEmail(e);
        admin.setPhoneNumber("08051185104");
        admin.setPassword("pxstar");
        userService.add(admin);
        System.out.println(admin);



        Main main = new Main();
        User loggedInUser = null;
        while (true) {
            do {
                main.showMessage(main.loginOptions());
                loggedInUser = main.loginChoice(authenticationConsole);
            } while (loggedInUser == null);


            main.mainChoice(loggedInUser, main);
            loggedInUser = null;

            main.showMessage(main.exitOptions());
            int c = main.exitChoice();
            if(c==0){
                break;
            }
        }

        //main.showMessage(main.userOptions());
        //main.userChoice(userConsole);
        BuildingConsole buildingConsole = new BuildingConsole(scanner, buildingService);
        RequestConsole requestConsole = new RequestConsole(scanner, requestService);
        while(true){
            main.showMessage(main.requestOptions());
            main.requestChoice(requestConsole);
        }

    }

    public int mainChoice(User loggedInUser, Main main){
        switch (loggedInUser.getUserType()){

            case ADMINISTRATOR:
                AdministratorConsole administratorConsole = new AdministratorConsole(scanner, userService);
                int c = 20;
                do{
                    main.showMessage(main.adminOptions());
                    c = main.adminChoice(administratorConsole, loggedInUser);
                }while (c!=0);
                break;
            case TECHNICIAN:
                System.out.println(loggedInUser);
                break;
            case DEPENDANT:
                System.out.println(loggedInUser);
                break;
            case MANAGER:
                ManagerConsole managerConsole = new ManagerConsole(scanner, userService, loggedInUser);
                BuildingManagerConsole buildingManagerConsole = new BuildingManagerConsole(scanner, userService, buildingService, loggedInUser);
                int i= 23;
                do{
                    main.showMessage(main.managerOptions());
                    i = main.managerChoice(managerConsole, buildingManagerConsole);
                }while (i!= 0);
                break;
            case TENANT:
                System.out.println(loggedInUser);
                TenantConsole tenantConsole = new TenantConsole(scanner, userService, loggedInUser);
                int r= 23;
                do{
                    main.showMessage(main.tenantOptions());
                    r = main.tenantChoice(tenantConsole);
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

    public String userOptions(){
        return "" +
                "1. Add user\n" +
                "2. Show users\n" +
                "3. Get user\n" +
                "4. Remove user\n" +
                "5. Login user\n" +
                "0. Exit\n" +
                "";
    }

    public String buildingOptions(){
        return "" +
                "1. Add Building\n" +
                "2. Show buildings\n" +
                "3. Get building\n" +
                "4. Remove building\n" +
                "0. Exit\n" +
                "";
    }

    public String requestOptions(){
        return "" +
                "1. Add Request\n" +
                "2. Show requests\n" +
                "3. Get request\n" +
                "4. Remove request\n" +
                "0. Exit\n" +
                "";
    }

    public String loginOptions(){
        return "" +
                "1. Login\n" +
                "2. Manager Sign-up\n" +
                "0. Exit\n" +
                "";
    }

    public String adminOptions(){
        return "" +
                "1. Approve user\n" +
                "2. List users\n" +
                "0. Back\n" +
                "";
    }

    public String managerOptions(){
        return "" +
                "1. Add Tenant\n" +
                "2. Remove Tenant\n" +
                "3. List tenants\n" +
                "4. Add Technician\n" +
                "5. Remove Technician\n" +
                "6. List Technicians\n" +
                "7. Manager details\n" +
                "8. Add Building\n" +
                "9. Remove Building\n" +
                "10. List Buildings\n" +
                "11. Assign tenant to building\n" +
                "12. De-assign tenant to building\n" +
                "13. List building tenants\n" +
                "0. Back\n" +
                "";
    }

    public String tenantOptions(){
        return "" +
                "1. Add Dependant\n" +
                "2. Remove Dependant\n" +
                "3. List Dependants\n" +
                "4. Tenant details\n" +
                "0. Back\n" +
                "";
    }

    public void showMessage(String msg){
        System.out.println(msg);
    }

    public void userChoice(UserConsole userConsole){
        int selection = getNumber();
        switch (selection){
            case 1:
                userConsole.newUser();
                break;
            case 2:
                userConsole.listUsers();
                break;
            case 3:
                userConsole.getUser();
                break;
            case 4:
                userConsole.removeUser();
                break;
            case 5:
                userConsole.loginUser();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    public void buildingChoice(BuildingConsole buildingConsole){
        int selection = getNumber();
        switch (selection){
            case 1:
                buildingConsole.newBuilding();
                break;
            case 2:
                buildingConsole.listBuildings();
                break;
            case 3:
                buildingConsole.getBuilding();
                break;
            case 4:
                buildingConsole.removeBuilding();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    public void requestChoice(RequestConsole requestConsole){
        int selection = getNumber();
        switch (selection){
            case 1:
                requestConsole.newRequest();
                break;
            case 2:
                requestConsole.listRequests();
                break;
            case 3:
                requestConsole.getRequest();
                break;
            case 4:
                requestConsole.removeRequest();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    public User loginChoice(AuthenticationConsole console){
        int selection = getNumber();
        switch (selection){
            case 1:
                return console.loginUser();
            case 2:
                console.managerSignup();
            case 0:
            default:
                return null;
        }
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

    public int adminChoice(AdministratorConsole console, User admin){
        int selection = getNumber();
        switch (selection){
            case 1:
                console.approveUser(admin);
                return 1;
            case 2:
                console.listUsers();
                return 2;
            case 0:
                return 0;
            default:
                return 10;
        }
    }

    public int managerChoice(ManagerConsole console, BuildingManagerConsole managerConsole){

        int selection = getNumber();
        switch (selection){
            case 1:
                console.addTenant();
                return 1;
            case 2:
                console.removeTenant();
                return 2;
            case 3:
                console.listTenants();
                return 3;
            case 4:
                console.addTechnician();
                return 4;
            case 5:
                console.removeTechnician();
                return 5;
            case 6:
                console.listTechnicians();
                return 6;
            case 7:
                console.managerDetails();
                return 7;
            case 8:
                managerConsole.addBuilding();
                return 8;
            case 9:
                managerConsole.removeBuilding();
                return 9;
            case 10:
                managerConsole.listBuildings();
                return 10;
            case 11:
                managerConsole.assignTenant();
                return 8;
            case 12:
                managerConsole.deAssignTenant();
                return 9;
            case 13:
                managerConsole.listBuildingOccupants();
                return 10;
            case 0:
                return 0;
            default:
                return 10;

        }
    }

    public int tenantChoice(TenantConsole console){

        int selection = getNumber();
        switch (selection){
            case 1:
                console.addDependant();
                return 1;
            case 2:
                console.removeDependant();
                return 2;
            case 3:
                console.listDependants();
                return 3;
            case 4:
                console.tenantDetails();
                return 3;
            case 0:
                return 0;
            default:
                return 10;

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

    public String getText(){
        Scanner scanner = new Scanner(System.in);
        String c = "";
        try{
            c = scanner.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Enter text");
        }
        scanner.close();
        return c;
    }
}
