package Entities;

import Controllers.Customer.OrderHistory;

public class Order { //Order class that has a method that shows orderHistory
    public void showOrderHistory(){
        OrderHistory orderHistory = new OrderHistory(); //OrderHistory class that interacts with .Properties Database
        orderHistory.showHistory();
    }
}
