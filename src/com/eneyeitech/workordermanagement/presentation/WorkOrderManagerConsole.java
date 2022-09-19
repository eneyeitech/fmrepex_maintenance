package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.RequestService;
import com.eneyeitech.requestmanagement.business.Status;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserService;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.usermanagement.business.user.Technician;
import com.eneyeitech.workordermanagement.business.WORequestService;
import com.eneyeitech.workordermanagement.business.WorkOrder;
import com.eneyeitech.workordermanagement.business.WorkOrderService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class WorkOrderManagerConsole {
    private Scanner scanner;
    private UserService userService;
    private WorkOrderService workOrderService;
    private RequestManagerConsole requestManagerConsole;
    private User manager;

    public WorkOrderManagerConsole(Scanner scanner, UserService userService, WorkOrderService workOrderService, RequestService tenantRequestService, WORequestService managementRequestService, User manager){
        this.scanner = scanner;
        this.userService = userService;
        this.workOrderService = workOrderService;
        this.manager = manager;
        requestManagerConsole = new RequestManagerConsole(scanner, userService, tenantRequestService, managementRequestService, manager);
    }

    public WorkOrder createWorkOrder(){
        if(!isManagerAndApproved()){
            return null;
        }
        showPrompt("Create a new work order");

        Request requestToAssign = requestManagerConsole.getRequest();
        if(requestToAssign == null){
            return null;
        }
        if(requestToAssign.getStatus() != Status.PENDING){
            System.out.println("Work Order for request exist");
            return null;
        }
        String technicianEmail = getString("Enter technician email: ");
        User technician = (User) userService.get(technicianEmail);

        if(!validTechnician(technician)){
            return null;
        }

        AuthorisedWorkOrderBuilder workOrderBuilder = new AuthorisedWorkOrderBuilder(scanner);
        WorkOrder workOrderToAssign = workOrderBuilder.getWorkOrder();
        workOrderToAssign.setRequest(requestToAssign);
        workOrderToAssign.setTechnicianEmail(technician.getEmail());
        boolean added = workOrderService.add(workOrderToAssign);

        if(added){
            requestToAssign.setStatus(Status.ACTIVE);
            requestToAssign.setWorkOrderId(workOrderToAssign.getId());
            System.out.println(workOrderToAssign);
            System.out.println("Work order created!");
        } else {
            System.out.println("Work order already exist");
        }
        return workOrderToAssign;
    }

    public void removeWorkOrder(){
        showPrompt("Remove a work order");
        String workOrderId = getWorkOrderId();
        boolean removed = workOrderService.remove(workOrderId);
        if(removed){
            System.out.println("Work order removed!");
        } else {
            System.out.println("Work order does not exist!");
        }
    }

    public WorkOrder getWorkOrder(){
        showPrompt("Get a work order");
        String workOrderId = getWorkOrderId();
        WorkOrder workOrder = workOrderService.get(workOrderId);
        if (workOrder==null){
            System.out.println("Work order does not exist!");
        }else{
            System.out.println(workOrder);
        }
        return workOrder;
    }

    public void listWorkOrders(){
        showPrompt("List of work orders!");
        String technicianEmail = getString("Enter technician email (leave blank to return all work orders)");
        List<WorkOrder> list;
        if(technicianEmail.isBlank() || technicianEmail.isEmpty()){
            list = workOrderService.getAll();
        }else{
            list = workOrderService.getAll(technicianEmail);
        }
        int i = 0;
        for(WorkOrder workOrder:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = workOrder.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
        }
    }

    public void listAcceptedWorkOrders(){
        showPrompt("List of work orders!");
        String technicianEmail = getString("Enter technician email (leave blank to return all work orders)");
        List<WorkOrder> list;
        if(technicianEmail.isBlank() || technicianEmail.isEmpty()){
            list = workOrderService.getAll();
        }else{
            list = workOrderService.getAll(technicianEmail);
        }
        int i = 0;
        for(WorkOrder workOrder:list){
            if(workOrder.getStatus() == com.eneyeitech.workordermanagement.business.Status.ACTIVE) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formatDateTime = workOrder.getCreatedDateTime().format(format);
                System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
            }
        }
    }

    public void listTenantRequest(){
        requestManagerConsole.listTenantRequests();
    }

    public void listRequest(){
        requestManagerConsole.listManagerRequestsView();
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private String getWorkOrderId(){
        return getString("Enter work order id: ");
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public boolean isManagerAndApproved(){
        return (isManager() && isApproved());
    }

    public boolean isApproved(){
        if(manager.isApproved()){
            return true;
        }else{
            System.out.println("Manager not approved");
            return false;
        }
    }

    public boolean isManager(){
        if(manager != null && manager.getUserType() != UserType.MANAGER){
            System.out.println("User not authorized to assign technician");
            return false;
        }
        return true;
    }

    public boolean isTechnician(User technician){
        if(technician != null && technician.getUserType() != UserType.TECHNICIAN){
            System.out.println("User not a technician");
            return false;
        }
        return true;
    }

    public boolean addedTechnician(User technician){
        if(manager.getEmail() == ((Technician) technician).getManagerEmail()){
            return true;
        }
        System.out.println("Technician not added by manager");
        return false;
    }

    public boolean validTechnician(User technician){
        return (isTechnician(technician) && addedTechnician(technician));
    }
}
