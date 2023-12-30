package Controllers.Customer;

import Controllers.Authentication;
import Controllers.Menu;
import Controllers.UserSession;
import Entities.Order;
import Entities.Product;

import java.util.List;
import java.util.Scanner;

public class ProductMenuCustomer { // This the product menu of the customer

    UserSession userSession;

    private ProductControllerCustomer productControllerCustomer = new ProductControllerCustomer(); //Instantiating the productController of the customer
    public ProductMenuCustomer(UserSession userSession)
    {
        this.userSession=userSession; //constructor initialising the userSession
    }

    public void showProductMenu(){ //showing the product menu
        System.out.println("Welcome to the Product Section !");

        Scanner scanner = new Scanner(System.in);
        System.out.println("PRESS 0 TO Search Products: ");
        System.out.println("PRESS 1 TO Read All Products: ");
        System.out.println("PRESS 2 To view your Shopping Cart: ");
        System.out.println("PRESS 3 To view your Order History: ");
        System.out.println("PRESS 4 TO Return: ");
        System.out.println("PRESS 5 TO LOGOUT:");
        System.out.println("PRESS 6 TO EXIT:");
        int choix=scanner.nextInt();
        scanner.nextLine();
        switch (choix)
        {
            case 0: //User chose to search and filter products
            {
                productControllerCustomer.readAllProductsWithoutAffichage(); //reading the products from database without printing them(only putting them in the productlist)
                if (productControllerCustomer.productList.size()>0)
                {
                    SearchAndFiltering searchAndFiltering = new SearchAndFiltering(productControllerCustomer.productList,userSession); //Instantiating the search and filtering
                    searchAndFiltering.showSearchFilteringMenu(); //Showing the filtering Menu.
                }
                else
                {
                    System.out.println("There are no items left! Wait till we add one first!\n ");
                    showProductMenu(); //Showing the product menu
                }
                break;

            }
            case 1: //show all products
            {
                productControllerCustomer.readAllProducts(); //showing all products
                if (productControllerCustomer.productList.size()>0) {
                    System.out.println("Select a product: ");
                    int choix2 = scanner.nextInt();
                    scanner.nextLine();
                    ProductSelectedMenuCustomer productSelectedMenuCustomer = new ProductSelectedMenuCustomer(productControllerCustomer.productList.get(choix2 - 1), this.userSession);
                    productSelectedMenuCustomer.showProductSelectedMenu(); //selecting product and showing product selected menu
                }
                else //product inventory is empty
                {
                    System.out.println("There are no items left! Wait till we add one first!\n ");
                    showProductMenu();
                }
                break;
            }
            case 2: //showing shopping cart
            {
                ShoppingCartController shoppingCartController = new ShoppingCartController(); //instantiating the shoppingcart
                shoppingCartController.createShoppingCart();// loading it from database

                shoppingCartController.readAllProducts(); //reading products and printing them
                if (!shoppingCartController.shoppingCart.getProductList().isEmpty()) //shopping cart has items in it
                {
                    System.out.println("Select a product: ");
                    List<Product> productList=shoppingCartController.shoppingCart.getProductList();
                    int choix2 = scanner.nextInt();
                    scanner.nextLine(); //selecting a product
                    ShoppingCartProductSelectedMenu shoppingCartProductSelectedMenu = new ShoppingCartProductSelectedMenu(productList.get(choix2 - 1), this.userSession); //instantiating product selected menu base on the product instance selected
                    shoppingCartProductSelectedMenu.showProductSelectedMenu(); //showing product selected menu
                }
                else //empty shoppingcart
                {
                    System.out.println("Shopping Cart Empty! Add Products First!\n ");
                    showProductMenu();
                }
                break;
            }
            case 3:{ //Order History
                Order order = new Order();
                order.showOrderHistory(); //showing orderHistory and orderHistoryMenu
                System.out.println("Press 0 to return:");
                int choix3= scanner.nextInt();
                scanner.nextLine();
                switch (choix3){
                    case 0:{ //returning
                        showProductMenu();
                    }
                    default:{ //wrong choice
                        showProductMenu();
                    }
                }
                break;
            }
            case 4: //return
            {
                HomeControllerCustomer homeControllerCustomer=new HomeControllerCustomer(userSession);
                homeControllerCustomer.showWelcomePage(); //showing welcome page
                break;
            }

            case 5: //logout
            {
                Authentication auth=Authentication.getInstance(); //instantiating the authentication class
                auth.logout(); //logout method
                System.out.println("Logout Successful!\n");
                Menu menu= new Menu();
                menu.showLoginMenu(); //showing login menu
                break;
            }
            case 6: //exiting the program
                {
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            }
            default: //wrong choice
            {

                System.out.println("Wrong choice");
                showProductMenu(); //showing product menu
            }

        }
    }


}
