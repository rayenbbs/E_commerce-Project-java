package Controllers.Customer;

import Controllers.Customer.PaymentMethods;

import java.util.Scanner;

public class PaymentPayPal implements PaymentMethods {
    //This is a simulation of a payment system by Paypal
    //This is PaymentPaypal class that is instantiated when the user chooses the Payment by PayPal option.
    //This class implements the PaymentMethods interface using a Strategy design Pattern.

    private String email;
    private String password;

    public void collectPaymentDetails(){ //Get User email and password for paypal account
        System.out.println("Type in your email");
        Scanner scanner = new Scanner(System.in);
        email= scanner.nextLine();
        System.out.println("Type in your password");
        password= scanner.nextLine();
    }
    public void pay(int amount){
        System.out.println("Paid "+ amount + "$ by Paypal");
    } //Pay by PayPal
}
