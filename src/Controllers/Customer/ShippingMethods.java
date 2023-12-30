package Controllers.Customer;

import Entities.Product;

import java.util.Scanner;

public abstract class ShippingMethods { //This is the abstract ShippingMethods Class that is extended by ShippingExpress
    //and ShippingStandard shipping options chosen by the user while doing the payment
    //It has the showShippingDelay abstract method as the ShippingDelay changes depending on the chosen Shipping Option
    private String name;
    private String address;

    public ShippingMethods(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public abstract void showShippingDelay();

}
