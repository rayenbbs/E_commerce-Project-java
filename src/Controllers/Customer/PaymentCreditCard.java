package Controllers.Customer;

import Entities.CreditCard;

import java.util.Scanner;

public class PaymentCreditCard implements PaymentMethods {
    //This is a simulation of a payment system by CreditCard
    //This is a PaymentCreditCard class that is instantiated when the user chooses the Payment by CreditCard option.
    //This class implements the PaymentMethods interface using a Strategy design Pattern.
    private CreditCard card;

    public void collectPaymentDetails(){ //Get User CreditCard info
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in your cardNumber");
        String cardNumber = scanner.nextLine();
        System.out.println("Type in your expiryDate");
        String expiryDate = scanner.nextLine();
        System.out.println("Type in your cvv");
        String cvv = scanner.nextLine();
        card=new CreditCard(cardNumber,expiryDate,cvv);
    }
    public void pay(int amount){ //Pay by CreditCard
        System.out.println("Paid "+amount+"$ by creditcard.");
        card.setBalance(card.getBalance()-amount);
    }
}
