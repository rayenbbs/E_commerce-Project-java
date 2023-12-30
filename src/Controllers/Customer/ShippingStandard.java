package Controllers.Customer;

import Controllers.Customer.ShippingMethods;

public class ShippingStandard extends ShippingMethods { //This is the ShippingStandard option that extends the abstract ShippingMethods class.
    //It implements the showShippingDelay abstract method of the ShippingMethods abstract class.
    public ShippingStandard(String name, String address) {
        super(name, address);
    }

    @Override
    public void showShippingDelay() {
        System.out.println("Shipping confirmed by standard shipping you'll have your order delivered in 5 days !");
    }
}
