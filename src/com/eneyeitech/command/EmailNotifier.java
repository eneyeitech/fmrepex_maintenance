package com.eneyeitech.command;

import com.eneyeitech.authentication.Login;
import com.eneyeitech.authentication.Registration;
import com.eneyeitech.authentication.UserManagement;

public class EmailNotifier extends CommandObserver{
    public EmailNotifier(Command command){
        super(command);
    }
    @Override
    public void doUpdate(Command command) {
        if(command.isSuccessful()){
            if(command instanceof UserCommand){
                UserCommand userCommand = (UserCommand) command;
                userMessage(userCommand);
            } else if(command instanceof RequestCommand){
                RequestCommand requestCommand = (RequestCommand) command;
                requestMessage(requestCommand);
            } else if(command instanceof WorkOrderCommand){
                WorkOrderCommand workOrderCommand = (WorkOrderCommand) command;
                workOrderMessage(workOrderCommand);
            } else if(command instanceof BuildingCommand){
                BuildingCommand buildingCommand = (BuildingCommand) command;
                buildingMessage(buildingCommand);
            }
        }
    }

    public void userMessage(UserCommand command){
        if(command.isApproved()){
            displayMessage(command.userToRegister.getEmail() + " approved successfully.");
        }
        if(command.added){
        System.out.printf("%s(%s) registered by %s(%s).\n",
                command.userToRegister.getFullName(),
                command.userToRegister.getEmail(),
                command.loggedInUser.getFullName(),
                command.loggedInUser.getEmail()
        );
        }
    }
    public void buildingMessage(BuildingCommand command){
        if(command.buildingAssigned){
            displayMessage("Building assigned successfully");
        }
        if(command.buildingCreated){
            displayMessage("Building created successfully");
        }
        if(command.buildingEdited){
            displayMessage("Building edited successfully");
        }
        if(command.buildingDeAssigned){
            displayMessage("Building de assigned successfully");
        }
    }

    public void workOrderMessage(WorkOrderCommand command){
        if(command.isWorkOrderCreated()){
            displayMessage("Work order created and assigned");
        }
        if(command.isRequestActivated()){
            displayMessage("Work order accepted");
        }
        if(command.isRequestCompleted()){
            displayMessage("Work order completed");
        }
    }

    public void requestMessage(RequestCommand command){
        if(command.isRequestCreated()){
            System.out.printf("%s(%s) created by %s(%s).\n",
                    command.request.getAsset(),
                    command.request.getCategory(),
                    command.loggedInUser.getFullName(),
                    command.loggedInUser.getEmail()
            );
        }
        if (command.isRequestEdited()){
            System.out.printf("%s(%s) signed off by %s(%s).\n",
                    command.request.getAsset(),
                    command.request.getCategory(),
                    command.loggedInUser.getFullName(),
                    command.loggedInUser.getEmail()
            );
        }
    }

    private void displayMessage(String m){
        System.out.println(m);
    }
}
