package com.epam;

import com.epam.types.*;


public class Client {

    public Client() {
    }

    public AbstractCommand initCommand(TypeCommand cmd) {
        AbstractCommand command = new BaseCommand();
        switch(cmd) {
            case LOGIN:

                command = new LoginCommand();
                break;
            case LOGOUT:

                command = new LogoutCommand();
                break;
        }
        return command;
    }



}
