package Controllers.Customer;

import Controllers.Admin.ProductControllerAdmin;
import Controllers.Admin.ProductMenuAdmin;
import Controllers.UserSession;
import Entities.Product;

import java.util.Scanner;

public class ProductSelectedMenuCustomer { //Menu that shows up when a product is selected
    Product product; //It takes product instance as attribute
    UserSession userSession; //It takes userSession instance as attribute

    public ProductSelectedMenuCustomer(Product product, UserSession userSession) {
        this.product = product;
        this.userSession = userSession;
    }

    void showProductSelectedMenu() {
        ProductControllerCustomer productControllerCustomer = new ProductControllerCustomer();
        System.out.println("PRESS 1 TO view product details: ");
        System.out.println("PRESS 2 TO add product to shopping cart: ");

        System.out.println("PRESS 4 TO Return: ");

        Scanner scanner = new Scanner(System.in);

        int choix = scanner.nextInt();
        scanner.nextLine();
        switch (choix) {
            case (1): { //viewing details of the product
                product.showProduct();
                showProductSelectedMenu();
                break;
            }
            case (2): { //adding product to shopping cart
                ShoppingCartController shoppingCartController = new ShoppingCartController();
                System.out.println("Type the Quantity you want to add: "); //getting the quantity user wants to add
                int quantity = scanner.nextInt();
                scanner.nextLine();
                shoppingCartController.addProduct(product.getBarCode(), quantity); //adding product to using the shoppingCartController method
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu(); //Showing the product menu
                break;
            }
            case (4): { //returning
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu(); //showing the product menu
                break;
            }
            default:{ //bad input
                System.out.println("Wrong choice! ");
                showProductSelectedMenu(); //reloading the current menu
            }
        }
    }
}
