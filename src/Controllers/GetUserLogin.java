package Controllers;

import java.util.Scanner;

public class GetUserLogin { //class that handles user input when it comes to Login info.
    private String password;
    protected String Identifier;
    public String getIdentifier(){
        return this.Identifier;
    }
    public void setIdentifier() { //getting userID from input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your userID: ");
        this.Identifier=scanner.nextLine();
    }

    public void setPassword(){ //getting user password from input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your Password: ");
        this.password=scanner.nextLine();

    }

    public String getPassword(){
        return (this.password);
    }
};
