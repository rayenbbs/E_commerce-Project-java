package Controllers.Admin;

import Controllers.Authentication;
import Controllers.Menu;
import Controllers.UserSession;

import java.util.Scanner;

public class ProductMenuAdmin {
    UserSession userSession;

    private ProductControllerAdmin productControllerAdmin = new ProductControllerAdmin();
    public ProductMenuAdmin(UserSession userSession)
    {
        this.userSession=userSession;
    } //constructor
    public void showProductMenu(){ //product menu of the admin
        System.out.println("Welcome to the Product Section !");
        Scanner scanner = new Scanner(System.in);
        System.out.println("PRESS 1 TO Read All Products: ");
        System.out.println("PRESS 3 TO Create a product: ");
        System.out.println("PRESS 6 TO Return: ");
        System.out.println("PRESS 7 TO LOGOUT:");
        System.out.println("PRESS 8 TO EXIT:");

        int choix=scanner.nextInt();
        scanner.nextLine();

        switch (choix)
        {
            case 1:{ //reading all products
                System.out.println("Select a product: ");
                productControllerAdmin.readAllProducts();
                if (productControllerAdmin.productList.size()>0) {
                    int choix2 = scanner.nextInt();
                    scanner.nextLine();
                    ProductSelectedMenuAdmin productSelectedMenuAdmin = new ProductSelectedMenuAdmin(productControllerAdmin.productList.get(choix2 - 1), this.userSession);
                    productSelectedMenuAdmin.showProductSelectedMenu();
                }
                else //product database is empty
                {
                    System.out.println("There are no items left! Create one first!\n ");
                    showProductMenu();
                }
                break;
            }
            case 3: //creating a product
            {
                System.out.println("Select the category:\n 1- Electronics\n 2- Clothing\n ");
                int choixCreate = scanner.nextInt();
                scanner.nextLine();
                switch (choixCreate){ //choosing the category
                    case 1: { //creating the item
                        System.out.println("Enter the product Barcode of the item you want to create: ");
                        int barcode=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter its name: ");
                        String name=scanner.nextLine();
                        System.out.println("Enter its price: ");
                        int price=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter its inStock: ");
                        int inStock=scanner.nextInt();
                        scanner.nextLine();
                        String type;
                        System.out.println("Enter its type: ");
                        type= scanner.nextLine();
                        String brand;
                        System.out.println("Enter its brand: ");
                        brand= scanner.nextLine();
                        if (productControllerAdmin.createElectronicsProduct(barcode,name,price,inStock,type,brand))
                        {
                            System.out.println("Product created succesfully");
                        }
                        else {
                            System.out.println("Item Already exists");
                        }
                        showProductMenu();
                        break;
                    }
                    case 2:{ //creating the item
                        System.out.println("Enter the product Barcode of the item you want to create: ");
                        int barcode=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter its name: ");
                        String name=scanner.nextLine();
                        System.out.println("Enter its price: ");
                        int price=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter its inStock: ");
                        int inStock=scanner.nextInt();
                        scanner.nextLine();
                        String type;
                        System.out.println("Enter its type: ");
                        type= scanner.nextLine();
                        String size;
                        System.out.println("Enter its size: ");
                        size= scanner.nextLine();
                        if (productControllerAdmin.createClothingProduct(barcode,name,price,inStock,type,size))
                        {
                            System.out.println("Product created succesfully");
                        }
                        else {
                            System.out.println("Item Already exists");
                        }
                        showProductMenu();
                        break;
                    }
                    default:{
                        System.out.println("Wrong choice. ");
                        showProductMenu();
                    }
                }

            }

            case 6: //returning to welcome page
            {
                HomeControllerAdmin homeControllerAdmin=new HomeControllerAdmin(userSession);
                homeControllerAdmin.showWelcomePage();
                break;
            }
            case 7: //logging out
            {
                Authentication auth=Authentication.getInstance();
                auth.logout();
                System.out.println("Logout Successful!\n");

                Menu menu= new Menu();
                menu.showLoginMenu();
                break;
            }
            case 8: //exiting the program
            {
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            }
            default: //wrong choice
            {

                System.out.println("Wrong choice");
                showProductMenu();
            }

        }
    }
}
