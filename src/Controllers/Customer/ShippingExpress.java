package Controllers.Customer;

public class ShippingExpress extends ShippingMethods {//This is the ShippingExpress option that extends the abstract ShippingMethods class.
    //It implements the showShippingDelay abstract method of the ShippingMethods abstract class.
    public ShippingExpress(String name, String address) {
        super(name, address);
    }

    @Override
    public void showShippingDelay() {
        System.out.println("Shipping confirmed by Express shipping you'll have your order delivered in 2 days !");
    }
}
