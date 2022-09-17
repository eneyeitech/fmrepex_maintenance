package com.eneyeitech.requestmanagement.presentation;

import com.eneyeitech.requestmanagement.business.Category;
import com.eneyeitech.requestmanagement.business.Request;
import com.eneyeitech.requestmanagement.helper.RequestIdGenerator;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class AuthorisedRequestBuilder {
    private Scanner scanner;
    private Request request;
    private String description;
    private String asset;
    private Category category;
    private LocalDateTime createdDateTime;

    public AuthorisedRequestBuilder(Scanner scanner){
        this.scanner = scanner;
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
        request.setAsset(asset);
        request.setDescription(description);
        request.setCategory(category);
        request.setCreatedDateTime(LocalDateTime.now());
        return request;
    }
}
