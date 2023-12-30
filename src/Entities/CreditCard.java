package Entities;

public class CreditCard {//This is the CreditCard class that is used in the PaymentCreditCard class that simulates a payment system by creditcard.
    // This class has balance,cardNumber,expiryDate,cvv attributes.
    private int balance=200;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    public CreditCard(String cardNumber,String expiryDate,String cvv){
        this.cardNumber=cardNumber;
        this.expiryDate=expiryDate;
        this.cvv=cvv;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
