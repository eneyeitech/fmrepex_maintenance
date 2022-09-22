package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.command.Command;
import com.eneyeitech.command.WorkOrderCommand;
import com.eneyeitech.constant.Status;
import com.eneyeitech.usermanagement.business.User;
import com.eneyeitech.usermanagement.business.UserType;
import com.eneyeitech.workordermanagement.business.WorkOrder;
import com.eneyeitech.workordermanagement.business.WorkOrderService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class WorkOrderTechnicianConsole {
    private Scanner scanner;
    private WorkOrderService workOrderService;
    private User technician;

    public WorkOrderTechnicianConsole(Scanner scanner, WorkOrderService workOrderService, User technician){
        this.scanner = scanner;
        this.workOrderService = workOrderService;
        this.technician = technician;
    }

    public void acceptWorkOrder(){
        showPrompt("Accept a work order");
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }
        Command command = new WorkOrderCommand(technician, null, workOrder, workOrderService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void markWorkOrderAsComplete(){
        showPrompt("Accept a work order");
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }

        Command command = new WorkOrderCommand(technician, null, workOrder, workOrderService);
        new com.eneyeitech.command.EmailNotifier(command);
        command.actionRequester();
    }

    public void getWorkOrderStatus(){
        WorkOrder workOrder = getWorkOrder();
        if(workOrder == null){
            return;
        }
        System.out.println(workOrder.getStatus().toString());
    }

    public WorkOrder getWorkOrder(){
        if (!isTechnician()){
            return null;
        }
        showPrompt("Get a work order");
        String workOrderId = getWorkOrderId();
        WorkOrder workOrder = workOrderService.get(workOrderId);

        if (workOrder==null){
            System.out.println("Work order does not exist!");
        }else{
            if(!assignedWorkOrder(workOrder)){
                System.out.println("Technician not allowed");
                return null;
            }
            System.out.println(workOrder);
        }
        return workOrder;
    }

    public void listWorkOrders(){
        if (!isTechnician()){
            return;
        }
        showPrompt("List of work orders!");
        List<WorkOrder> list;
        list = workOrderService.getAll(technician.getEmail());
        int i = 0;
        for(WorkOrder workOrder:list){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = workOrder.getCreatedDateTime().format(format);
            System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
        }
    }

    public void listAcceptedWorkOrders(){
        if (!isTechnician()){
            return;
        }
        showPrompt("List of work orders!");
        List<WorkOrder> list;
        list = workOrderService.getAll(technician.getEmail());
        int i = 0;
        for(WorkOrder workOrder:list){
            if(workOrder.getStatus() == Status.ACTIVE) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formatDateTime = workOrder.getCreatedDateTime().format(format);
                System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), formatDateTime);
            }
        }
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



    private boolean isTechnician(){
        if(technician != null && technician.getUserType() != UserType.TECHNICIAN){
            System.out.println("User not a technician");
            return false;
        }
        return true;
    }

    private boolean assignedWorkOrder(WorkOrder workOrder){
        if(technician.getEmail() == workOrder.getTechnicianEmail()){
            return true;
        }
        return false;
    }

}
