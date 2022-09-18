package com.eneyeitech.workordermanagement.presentation;

import com.eneyeitech.workordermanagement.business.WorkOrder;
import com.eneyeitech.workordermanagement.helper.WorkOrderIdGenerator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class WorkOrderBuilder {
    private Scanner scanner;
    private WorkOrder workOrder;
    private String technicianEmail;
    private LocalDateTime createdDateTime;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime assignedDateTime;
    private LocalDateTime completedDateTime;
    private String description;
    public WorkOrderBuilder(Scanner scanner){
        this.scanner = scanner;
        technicianEmail = getString("Enter technician email: ");
        description = getString("Enter brief work description (note to technician): ");
    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public WorkOrder getWorkOrder(){
        workOrder = new WorkOrder(new WorkOrderIdGenerator(10));
        workOrder.setTechnicianEmail(technicianEmail);
        workOrder.setDescription(description);
        workOrder.setCreatedDateTime(LocalDateTime.now());
        return workOrder;
    }
}
