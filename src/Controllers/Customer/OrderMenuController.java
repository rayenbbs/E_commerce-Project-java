package Controllers.Customer;
import Controllers.*;
import Controllers.Admin.ProductControllerAdmin;
import Entities.Product;

import java.util.Scanner;

public class OrderMenuController { // OrderMenuController handles the order menu for a specific product and user session

    //  variables for the product and user session
    Product product;
    UserSession userSession;

    // constructor initializing the OrderMenuController with a product and user session
    public OrderMenuController(Product product, UserSession userSession){
        this.product = product;
        this.userSession = userSession;
    }
    public void showOrderMenu(){ // display the order menu and handle user input
        //  menu options
        System.out.println("This is the Order Menu!");
        System.out.println("Press 1 To ADD PROMOTION CODE!");
        System.out.println("Press 2 To PROCEED TO PAYMENT! ");
        System.out.println("Press 3 To Return to Product Menu!");


        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        scanner.nextLine();
        switch(choix){
            case 1:{
                // adding promotion code to the product
                if (!this.product.getPromotionAdded()) {
                    PromotionCodeController promotionCodeController = new PromotionCodeController();
                    System.out.println("Type in a valid promotion code!.");
                    String promotionCode = scanner.nextLine();
                    System.out.println("This is the old price per unit "+product.getPrice()+".");
                    this.product = promotionCodeController.usePromotionCode(promotionCode, product);
                    System.out.println("This is the new price per unit "+product.getPrice()+"."); //outputting the new price per unit
                }
                else{
                    System.out.println("You already added a promotion code to this item!"); //promotion already applied
                }
                showOrderMenu();// show Order Menu after promotion applied
                break;
            }
            case 2:{
                //payment and choose payment method
                System.out.println("Choose the method of payment:");
                System.out.println("1- Paypal");
                System.out.println("2- Credit card");
                PaymentMethods paymentMethods = null;
                int choix2=scanner.nextInt();
                scanner.nextLine();
                switch (choix2){
                    case 1:{
                        paymentMethods = new PaymentPayPal(); //payment by paypal
                        break;
                    }
                    case 2:{
                        paymentMethods = new PaymentCreditCard();break; //payment by creditcard
                    }
                    default: {
                        System.out.println("Order Cancelled");
                        showOrderMenu();
                    }
                }
                // process payment if a valid payment method is selected
                if (paymentMethods!=null) {
                    paymentMethods.collectPaymentDetails();
                    System.out.println("\n\n             -------------------------\n\n");

                    // choose the method of shipping
                    System.out.println("Choose the method of shipping:");
                    System.out.println("1- Standard Shipping (normal)");
                    System.out.println("2- Express Shipping (fast)");
                    ShippingMethods shippingMethods = null;
                    int choix3=scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Type in your name");
                    String name= scanner.nextLine();
                    System.out.println("Type in your address");
                    String address= scanner.nextLine();
                    switch (choix3){
                        case 1:{
                            shippingMethods = new ShippingStandard(name,address);break; //chose shipping standard
                        }
                        case 2:{
                            shippingMethods = new ShippingExpress(name,address);break; //chose shipping express
                        }
                        default: {
                            System.out.println("Order Cancelled"); //invalid choice
                            showOrderMenu();
                        }
                    }
                    // process order if a shipping method is selected
                    if (shippingMethods!=null){
                        System.out.println("Order Processed Successfully!");
                        paymentMethods.pay(this.product.getPrice()*this.product.getInStock()); //process the payment
                        shippingMethods.showShippingDelay(); //show the shipping delay depending on the chosen shipping option
                        ShoppingCartController shoppingCartController = new ShoppingCartController();
                        shoppingCartController.deleteItemFromShoppingCart(this.product.getBarCode(),0); //delete the item from shopping cart
                        ProductControllerAdmin productControllerAdmin = new ProductControllerAdmin();
                        productControllerAdmin.updateProductOrdered(product.getBarCode(),product.getInStock()); //update the inventory
                        OrderHistory orderHistory = new OrderHistory();
                        orderHistory.storeOrder(this.product); //store the order in orderhistory
                    }
                }
                break;
            }
            case 3: {
                // return to customer product menu
                ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                productMenuCustomer.showProductMenu();
                break;
            }
            default:
                // bad choice
                showOrderMenu();
        }
    }
}
