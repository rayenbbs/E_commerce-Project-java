
import Controllers.Menu;

public class Main { //This is the main class
    public static void main(String[] args) {
        System.out.println("Welcome to my new E-commerce Console Application");
        Menu menu = new Menu();
        menu.showLoginMenu(); // This is the loginMenu that will allow to access other menus depending on the choices you make.
    }
}