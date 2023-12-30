package Controllers.Admin;

import Controllers.UserSession;
import Entities.Product;
import Entities.User;

import java.util.Scanner;

public class ProductSelectedMenuAdmin {

    Product product;
    UserSession userSession;

    public ProductSelectedMenuAdmin(Product product, UserSession userSession){ //constructor
            this.product=product;
            this.userSession=userSession;
    }
    void showProductSelectedMenu(){ //showing the product selected menu for the admin
        ProductControllerAdmin productControllerAdmin = new ProductControllerAdmin();
        System.out.println("PRESS 1 TO view product details: ");
        System.out.println("PRESS 2 TO Update the product: ");
        System.out.println("PRESS 3 TO Delete the product: ");
        System.out.println("PRESS 4 TO Return: ");

        Scanner scanner = new Scanner(System.in);

        int choix=scanner.nextInt();
        scanner.nextLine();
        switch (choix) {
            case (1): { //view product details
                    product.showProduct();
                    showProductSelectedMenu();
            }
            case (2):{ //update the product new name new price new quantity(instock)
                System.out.println("Enter its new name: ");
                String name=scanner.nextLine();

                System.out.println("Enter its new price ");
                int price=scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter its new inStock ");
                int inStock=scanner.nextInt();
                scanner.nextLine();

                productControllerAdmin.updateProduct(this.product.getBarCode(),name,price,inStock);
                ProductMenuAdmin productMenuAdmin = new ProductMenuAdmin(userSession);
                productMenuAdmin.showProductMenu();
            }

            case(3):{ //deleting the product by its barcode
                productControllerAdmin.deleteProduct(this.product.getBarCode());
                ProductMenuAdmin productMenuAdmin = new ProductMenuAdmin(userSession);
                productMenuAdmin.showProductMenu();
            }
            case(4):{ //returning to product menu
                ProductMenuAdmin productMenuAdmin = new ProductMenuAdmin(userSession);
                productMenuAdmin.showProductMenu();
            }
        }

    }
}
