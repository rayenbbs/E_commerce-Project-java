package Controllers;


import Controllers.Admin.AdminSession;
import Controllers.Customer.CustomerSession;
import DataBase.DBLoader;

import java.util.Properties;

public class Authentication { //This is the Authentication Class that handles the User Authentication and Logout
    private UserSession userSession;
    private Properties loginInfo = new Properties();//The properties file that has all the login Info
    private DBLoader DBloader = DBLoader.getInstance(); //It has access to the Database

    private static volatile Authentication instance;

    private Authentication()
    {
        this.loginInfo = DBloader.getLoginInfo();
        this.userSession=null;
    }

    public static Authentication getInstance(){ //SINGLETON DESIGN PATTERN BECAUSE AUTHENTICATION SHOULD BE INSTANCIATED ONCE
        Authentication result=instance;
        if (result==null){
            synchronized (Authentication.class){
                result=instance;
                if (result==null){
                    instance = result = new Authentication();
                }
            }
        }
        return result;
    }

    public UserSession login(GetUserLogin getUserLogin) { //Login Method
        String identifier=getUserLogin.getIdentifier(); //Getting the ID
        String password=getUserLogin.getPassword(); //getting the Password
        String userInfo=loginInfo.getProperty(identifier); //Accessing the databse by the ID

        if (userInfo!=null) { //The ID is valid
            String[] passAndRole = (userInfo.split(",")); //Split the String that is value of the String ID in the properties file.
            String storedPassword=passAndRole[0]; //the password
            if (storedPassword.equals(password))
            {
                String storedRole=passAndRole[1]; //The role
                if (storedRole.equals("Admin")) //Get AdminSession Instance
                {
                    this.userSession= AdminSession.getInstance(identifier);
                    return this.userSession;
                }
                else if (storedRole.equals("Customer")) { //Get CustomerSession Instance
                    this.userSession= CustomerSession.getInstance(identifier);
                    return this.userSession;
                }
                else
                    return null;
            }
            else
                return null;
        }
        else
            return null;
    }

    public void logout() //Logout method
    {
        this.userSession=null; //Put the UserSession to null because the user has logged out
        System.out.println("Terminating Session...");
    }
}
