package Controllers.Customer;

public interface PaymentMethods { // This is the PaymentMethods interface used in a Strategy design pattern context.
    void collectPaymentDetails(); //Get User Details for according to the payment method chosen
    void pay(int amount); //Do the payment according to the payment method chosen
}
