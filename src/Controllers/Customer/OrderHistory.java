package Controllers.Customer;

import DataBase.DBLoader;
import Entities.Product;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OrderHistory { //orderHistory class
    private DBLoader dbLoader = DBLoader.getInstance(); //get dbLoader instance
    private Properties orderInfo = dbLoader.getOrderInfo(); //get the properties file

    public void updateProp(){ //update the .properties file after modifying it because it doesn't update automatically
        try (FileOutputStream outputStream = new FileOutputStream(DBLoader.ORDERFILE)) {
            orderInfo.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        orderInfo.clear();
        try (FileInputStream inputStream = new FileInputStream(DBLoader.ORDERFILE)) {
            orderInfo.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbLoader.setOrderInfo(orderInfo);
    }
    public void storeOrder(Product product){ //storing order in database
        updateProp();
        int i=0;
        while(orderInfo.getProperty(Integer.toString(i))!=null){
            ++i;
        }
        orderInfo.setProperty(Integer.toString(i),Integer.toString(product.getBarCode())+","+product.getName() + "," + Integer.toString(product.getInStock()));
        updateProp();
    }
    public void showHistory(){ //outputting the history from database
        updateProp();
        int i=0;
        if (orderInfo.getProperty(Integer.toString(i))==null) { //history database empty
            System.out.println("You never passed any orders!");
        }
        else { //database has items in it
            System.out.println("This is your Order history:");
            while (orderInfo.getProperty(Integer.toString(i)) != null) {
                String orderDetails = orderInfo.getProperty(Integer.toString(i));
                String[] details = (orderDetails.split(","));
                System.out.println(i+1+"- Product Name: "+details[1] + " Quantity: "+details[2]+".");
                ++i;
            }
        }
    }
}
