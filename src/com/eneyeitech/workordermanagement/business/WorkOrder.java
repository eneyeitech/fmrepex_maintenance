package com.eneyeitech.workordermanagement.business;

import com.eneyeitech.requestmanagement.business.Request;

import java.time.LocalDateTime;

public class WorkOrder {
    private String id;
    private Request request;
    private String technicianEmail;
    private LocalDateTime createdDateTime;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime assignedDateTime;
    private LocalDateTime completedDateTime;
    private String description;
}
