package com.eneyeitech.requestmanagement.presentation;

import com.eneyeitech.buildingmanagement.business.ManagedBuilding;
import com.eneyeitech.buildingmanagement.business.State;
import com.eneyeitech.buildingmanagement.helper.BuildingIdGenerator;
import com.eneyeitech.requestmanagement.business.Category;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.business.Status;
import com.eneyeitech.requestmanagement.helper.RequestIdGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class RequestBuilder {
    private Scanner scanner;
    private Request request;
    private String description;
    private String asset;
    private Category category;
    private LocalDateTime createdDateTime;
    private String tenantEmail = null;
    private String managerEmail = null;

    public RequestBuilder(Scanner scanner){
        this.scanner = scanner;
        tenantEmail = getString("Enter tenant email: ");
        managerEmail = getString("Enter manager email: ");
        asset = getString("Enter asset needing maintenance (bulb, tap etc...): ");
        description = getString("Enter brief description of problem: ");

        Category[] categories = Category.values();
        Random rand = new Random();

        int n = rand.nextInt(categories.length);
        category = categories[n];

    }

    private String getString(String msg){
        showPrompt(msg);
        return scanner.nextLine();
    }

    private void showPrompt(String msg){
        System.out.println(msg);
    }

    public Request getRequest(){
        request = new Request(new RequestIdGenerator(10));
        request.setTenantEmail(tenantEmail);
        request.setManagerEmail(managerEmail);
        request.setAsset(asset);
        request.setDescription(description);
        request.setCategory(category);
        request.setCreatedDateTime(LocalDateTime.now());
        return request;
    }
}
