package Controllers.Customer;
import Controllers.Authentication;
import Controllers.Menu;
import Controllers.UserSession;

import java.util.Scanner;

public class HomeControllerCustomer {

    UserSession userSession;

    public HomeControllerCustomer(UserSession userSession) {
        this.userSession = userSession;
    }

    public void showWelcomePage() { //showing the welcome page of the customer
        System.out.println("Welcome Customer " + userSession.getUserID() + " !");
        Scanner scanner = new Scanner(System.in);
        System.out.println("PRESS 1 TO LOGOUT: \n");
        System.out.println("PRESS 2 TO ACCESS PRODUCTS: \n");
        System.out.println("PRESS 3 TO EXIT:\n");

        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1: { //logout
                Authentication auth = Authentication.getInstance();
                auth.logout();
                System.out.println("Logout Successful!\n");
                Menu menu = new Menu();
                menu.showLoginMenu(); //return to login menu
                break;
            }
            case 2: { //accessing products menu
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu();
                break;
            }
            case 3: { //exiting the program
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            }
            default: { //bad choice
                System.out.println("Wrong choice");
                showWelcomePage();
            }

        }
    }
}
