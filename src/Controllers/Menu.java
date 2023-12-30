package Controllers;



import java.util.Scanner;
public class Menu { //This is the first menu that shows up

    public void showLoginMenu()
    {

        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("Enter your choice: ");

        Scanner scanner = new Scanner (System.in);
        int choix=scanner.nextInt();
        scanner.nextLine();
        switch (choix)
        {
            case 1:
            {
                Authentication auth = Authentication.getInstance();
                GetUserLogin getUserLogin = new GetUserLogin();
                getUserLogin.setIdentifier(); //get userID
                getUserLogin.setPassword();  //get User password
                UserSession userSession=auth.login(getUserLogin); //login on database
                if (userSession==null) {
                    System.out.println("Wrong UserID or password!\nTry again!"); //Wrong login info
                    showLoginMenu();
                }
                else

                    userSession.showWelcomePage(); //login succesful welcome page shows up
                break;
            }
            case 2:
            {
                System.out.println("Exiting the program."); // exit  the program
                System.exit(0);
                break;
            }
            default:
            {
                System.out.println("Wrong choice.");
                showLoginMenu();
            }
        }
    }


}
