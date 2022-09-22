package com.eneyeitech;

import com.eneyeitech.authentication.*;
import com.eneyeitech.buildingmanagement.business.BuildingService;
import com.eneyeitech.buildingmanagement.presentation.BuildingConsole;
import com.eneyeitech.buildingmanagement.presentation.BuildingManagerConsole;
import com.eneyeitech.command.Command;
import com.eneyeitech.command.UserCommand;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.requestmanagement.presentation.RequestConsole;
import com.eneyeitech.requestmanagement.presentation.RequestDependantConsole;
import com.eneyeitech.requestmanagement.presentation.RequestTenantConsole;
import com.eneyeitech.usermanagement.business.*;
import com.eneyeitech.usermanagement.business.user.YetToLogin;
import com.eneyeitech.usermanagement.presentation.*;
import com.eneyeitech.workordermanagement.business.WORequestService;
import com.eneyeitech.workordermanagement.business.WorkOrderService;
import com.eneyeitech.workordermanagement.presentation.*;

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
        userService = new UserService();
        scanner = new Scanner(System.in);

        UserConsole userConsole = new UserConsole(scanner, userService);
        AuthenticationConsole authenticationConsole = new AuthenticationConsole(scanner, userService);
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
        com.eneyeitech.console.AuthenticationConsole authenticationConsole1 = new com.eneyeitech.console.AuthenticationConsole(scanner, null);
        // Run main
        Main main = new Main();
        User loggedInUser = null;
        while (true) {
            do {
                authenticationConsole1.menuDisplay();
                loggedInUser = authenticationConsole1.handle();
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
        WorkOrderConsole workOrderConsole = new WorkOrderConsole(scanner, workOrderService);
        WORequestConsole woRequestConsole = new WORequestConsole(scanner, requestService, woRequestService);
        int v = 0;
        while(true){
            main.showMessage(main.workOrderOptions());
            main.workOrderChoice(workOrderConsole);
        }



    }

    static class MyThread extends Thread{

        public MyThread(int t){

        }
        public void run(){

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
                WorkOrderTechnicianConsole workOrderTechnicianConsole = new WorkOrderTechnicianConsole(scanner, workOrderService, loggedInUser);
                int t= 23;
                do{
                    main.showMessage(main.technicianOptions());
                    t = main.technicianChoice(workOrderTechnicianConsole);
                }while (t!= 0);
                break;
            case DEPENDANT:
                System.out.println(loggedInUser);
                RequestDependantConsole requestDependantConsole = new RequestDependantConsole(scanner, userService, requestService, loggedInUser);
                int q= 23;
                do{
                    main.showMessage(main.dependantOptions());
                    q = main.dependantChoice(requestDependantConsole);
                }while (q!= 0);
                break;
            case MANAGER:
                com.eneyeitech.console.ManagerConsole managerConsole1 = new com.eneyeitech.console.ManagerConsole(scanner, loggedInUser);
                int i= 23;
                do{
                    managerConsole1.menuDisplay();
                    i = managerConsole1.handleSelection();

                }while (i!= 0);
                break;
            case TENANT:
                System.out.println(loggedInUser);
                TenantConsole tenantConsole = new TenantConsole(scanner, userService, loggedInUser);
                RequestTenantConsole requestTenantConsole = new RequestTenantConsole(scanner, userService, requestService, loggedInUser);
                int r= 23;
                do{
                    main.showMessage(main.tenantOptions());
                    r = main.tenantChoice(tenantConsole, requestTenantConsole);
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

    public String workOrderOptions(){
        return "" +
                "1. Create Work Order\n" +
                "2. Show Work Orders\n" +
                "3. Get Work Order\n" +
                "4. Remove Work Order\n" +
                "0. Exit\n" +
                "";
    }

    public String technicianOptions(){
        return "" +
                "1. View Work Orders\n" +
                "2. View Work Order\n" +
                "3. Accept Work Order\n" +
                "4. View Status of a Work Order\n" +
                "5. View accepted Work Orders\n" +
                "6. Mark Work Order Complete\n" +
                "0. Back\n" +
                "";
    }

    public String woRequestOptions(){
        return "" +
                "1. Add Request\n" +
                "2. Show requests\n" +
                "3. Show WO requests\n" +
                "4. Get request\n" +
                "5. Remove request\n" +
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
                "14. List tenants request\n" +
                "15. List all request\n" +
                "16. View Request\n" +
                "17. Create Work Order\n" +
                "18. List Work Orders\n" +
                "19. View Work Order\n" +
                "20. View Accepted Work Orders\n" +
                "0. Back\n" +
                "";
    }

    public String tenantOptions(){
        return "" +
                "1. Add Dependant\n" +
                "2. Remove Dependant\n" +
                "3. List Dependants\n" +
                "4. Tenant details\n" +
                "5. Make request\n" +
                "6. View requests\n" +
                "7. Sign off request\n" +
                "0. Back\n" +
                "";
    }

    public String dependantOptions(){
        return "" +
                "1. View requests\n" +
                "2. View a request\n" +
                "3. Sign off request\n" +
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

    public void workOrderChoice(WorkOrderConsole workOrderConsole){
        int selection = getNumber();
        switch (selection){
            case 1:
                workOrderConsole.newWorkOrder();
                break;
            case 2:
                workOrderConsole.listWorkOrders();
                break;
            case 3:
                workOrderConsole.getWorkOrder();
                break;
            case 4:
                workOrderConsole.removeWorkOrder();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
            default:
        }
    }

    public int technicianChoice(WorkOrderTechnicianConsole console){
        int selection = getNumber();
        switch (selection){
            case 1:
                console.listWorkOrders();
                return 1;
            case 2:
                console.getWorkOrder();
                return 2;
            case 3:
                console.acceptWorkOrder();
                return 3;
            case 4:
                console.getWorkOrderStatus();
                return 4;
            case 5:
                console.listAcceptedWorkOrders();
                return 5;
            case 6:
                console.markWorkOrderAsComplete();
                return 6;
            case 0:
                return 0;
            default:
                return 99;
        }
    }

    public int dependantChoice(RequestDependantConsole console){
        int selection = getNumber();
        switch (selection){
            case 1:
                console.listRequests();
                return 1;
            case 2:
                console.getRequest();
                return 1;
            case 3:
                console.signOffRequest();
                return 2;
            case 0:
                return 0;
            default:
                return 99;
        }
    }

    public void woRequestChoice(WORequestConsole woRequestConsole){
        int selection = getNumber();
        switch (selection){
            case 1:
                woRequestConsole.newRequest();
                break;
            case 2:
                woRequestConsole.listRequests();
                break;
            case 3:
                woRequestConsole.woListRequests();
                break;
            case 4:
                woRequestConsole.getRequest();
                break;
            case 5:
                woRequestConsole.removeRequest();
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

    public int managerChoice(ManagerConsole console, BuildingManagerConsole managerConsole, RequestManagerConsole requestManagerConsole, WorkOrderManagerConsole workOrderManagerConsole){

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
            case 14:
                requestManagerConsole.listTenantRequests();
                return 8;
            case 15:
                requestManagerConsole.listManagerRequestsView();
                return 9;
            case 16:
                requestManagerConsole.getRequest();
                return 10;
            case 17:
                workOrderManagerConsole.createWorkOrder();
                return 10;
            case 18:
                workOrderManagerConsole.listWorkOrders();
                return 10;
            case 19:
                workOrderManagerConsole.getWorkOrder();
                return 10;
            case 20:
                workOrderManagerConsole.listAcceptedWorkOrders();
                return 10;
            case 0:
                return 0;
            default:
                return 10;

        }
    }

    public int tenantChoice(TenantConsole console, RequestTenantConsole tenantConsole){

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
                return 4;
            case 5:
                tenantConsole.newRequest();
                return 5;
            case 6:
                tenantConsole.listRequests();
                return 6;
            case 7:
                tenantConsole.signOffRequest();
                return 7;
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
