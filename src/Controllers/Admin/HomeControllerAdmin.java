package Controllers.Admin;


import Controllers.Authentication;
import Controllers.Menu;
import Controllers.UserSession;

import java.util.Scanner;

public class HomeControllerAdmin  { //this is the home controller of the admin

    UserSession userSession;
    public HomeControllerAdmin(UserSession userSession)
    {
        this.userSession=userSession;
    }
    public void showWelcomePage() //this is the welcome page of the admin
    {
        System.out.println("Welcome Admin "+userSession.getUserID()+" !");

        NotificationController notificationController = new NotificationController();
        System.out.println("Your notifications: -"); //showing the notifications in case there are events where an item is out of stock our low on stock
        notificationController.showNotifications();
        //options
        Scanner scanner = new Scanner(System.in);
        System.out.println("PRESS 1 TO LOGOUT: \n");
        System.out.println("PRESS 2 TO ACCESS PRODUCTS: \n");
        System.out.println("PRESS 3 TO EXIT:\n");
        int choix=scanner.nextInt();
        scanner.nextLine();
        switch (choix)
        {
            case 1: //logging out
            {
                Authentication auth=Authentication.getInstance();
                auth.logout();
                System.out.println("Logout Successful!\n");
                Menu menu= new Menu();
                menu.showLoginMenu();
                break;
            }
            case 2: //accessing products
            {
                ProductMenuAdmin productMenuAdmin = new ProductMenuAdmin(userSession);
                productMenuAdmin.showProductMenu();
                break;
            }
            case 3: //exiting the program
            {
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            }
            default: //wrong choice
            {
                System.out.println("Wrong choice");
                showWelcomePage();
            }

        }
    }
}
