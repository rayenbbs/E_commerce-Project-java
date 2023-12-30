package Controllers.Customer;

import Controllers.UserSession;
import Entities.Product;

import java.util.Scanner;

public class ShoppingCartProductSelectedMenu { //This is the Menu that shows up when a Product is selected from ShoppingCart
    //It takes a product instance and userSession instance as attributes.
    Product product;
    UserSession userSession;

    public ShoppingCartProductSelectedMenu(Product product, UserSession userSession) { //Constructor
        this.product = product; //Set the Product Instance
        this.userSession = userSession; //Set the UserSession Instance
    }

    public void showProductSelectedMenu() { //This is the Menu
        ShoppingCartController shoppingCartController = new ShoppingCartController();
        System.out.println("PRESS 1 TO DELETE THE ITEM: ");
        System.out.println("PRESS 2 TO UPDATE THE ITEM: ");
        System.out.println("PRESS 3 TO ORDER THE ITEM: ");
        System.out.println("PRESS 4 TO Return: ");

        Scanner scanner = new Scanner(System.in);

        int choix = scanner.nextInt();
        scanner.nextLine();
        switch (choix) {
            case (1): {
                shoppingCartController.deleteItemFromShoppingCart(product.getBarCode(),1); //Delete Item from shopping Cart '1' indicates that there will be output indicating the Item was deleted successfully
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu(); //Return to product menu
                break;
            }
            case (2): {
                System.out.println("Type the new Quantity you want to set: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                shoppingCartController.updateProduct(product.getBarCode(),quantity); //Updating the product in the shopping cart by a new quantity
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu(); //Return to product menu
                break;
            }
            case(3):{
                shoppingCartController.orderProduct(product.getBarCode(), userSession); //Ordering Product, Order Menu shows up
                break;
            }
            case (4): {
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu(); //Return to product menu
                break;
            }
        }
    }
}
