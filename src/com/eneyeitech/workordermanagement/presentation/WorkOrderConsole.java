package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.builder.RequestBuilder;
import com.eneyeitech.builder.WorkOrderBuilder;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.constant.Status;
import com.eneyeitech.workordermanagement.business.WorkOrder;
import com.eneyeitech.workordermanagement.business.WorkOrderService;

import java.util.List;
import java.util.Scanner;

public class WorkOrderConsole {
    private Scanner scanner;
    private WorkOrderService workOrderService;

    public WorkOrderConsole(Scanner scanner, WorkOrderService workOrderService){
        this.scanner = scanner;
        this.workOrderService = workOrderService;
    }


    public WorkOrder newWorkOrder(){
        showPrompt("Create a new work order");
        RequestBuilder requestBuilder = new RequestBuilder(scanner);
        Request requestToAdd = requestBuilder.getRequest();
        requestToAdd.setStatus(Status.ACTIVE);
        WorkOrderBuilder workOrderBuilder = new WorkOrderBuilder(scanner);
        WorkOrder workOrderToAdd = workOrderBuilder.getWorkOrder();
        workOrderToAdd.setRequest(requestToAdd);
        boolean added = workOrderService.add(workOrderToAdd);
        if(added){
            System.out.println(workOrderToAdd);
            System.out.println("Work order added!");
        } else {
            System.out.println("Work order already exist");
        }
        return workOrderToAdd;
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
            System.out.printf("%s: (%s) %s - %s.\n", ++i, workOrder.getId(), workOrder.getRequest().getAsset(), workOrder.getCreatedDateTime());
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
}
