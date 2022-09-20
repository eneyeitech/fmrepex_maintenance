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
                UserCommand user = (UserCommand) command;
                System.out.printf("%s(%s) registered by %s(%s).\n",
                        user.userToRegister.getFullName(),
                        user.userToRegister.getEmail(),
                        user.loggedInUser.getFullName(),
                        user.loggedInUser.getEmail()
                        );
            }
        }
    }
}
