package Controllers.Admin;

import DataBase.DBLoader;
import Entities.ClothingProduct;
import Entities.ElectronicsProduct;

import java.util.Properties;
import java.util.Set;

public class NotificationController { //this is the notification controller that gives the admin notifications when items are out of stock or low on stock
    private DBLoader dbLoader = DBLoader.getInstance();
    private Properties productInfo = dbLoader.getProductInfo();


    public boolean readProduct(int barCode) { //reading product from database by its ID
        String productDetails = productInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) { //item found in database
            String[] details = (productDetails.split(","));
            if (details[0].equals("Electronics")) { //reading electronic product
                ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                if (Integer.parseInt(details[2])<10 && Integer.parseInt(details[2])!=0){ //low stock
                    System.out.println("Electronics Item with name: "+electronicsProduct.getName()+" Barcode: "+electronicsProduct.getBarCode()+" is low on stock.");
                    System.out.println("Items left in stock: "+Integer.parseInt(details[2])+"\n");
                }
                if (Integer.parseInt(details[2])==0){ //out of stock
                    System.out.println("Electronics Item with name: "+electronicsProduct.getName()+" Barcode: "+electronicsProduct.getBarCode()+" is out of stock.\n");
                }
            }
            else if (details[0].equals("Clothing")) { //reading clothing product
                ClothingProduct clothingProduct = new ClothingProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                if (Integer.parseInt(details[2])<10 && Integer.parseInt(details[2])!=0){ //low stock
                    System.out.println("Clothing Item with name: "+clothingProduct.getName()+" Barcode: "+clothingProduct.getBarCode()+" is low on stock.");
                    System.out.println("Items left in stock: "+Integer.parseInt(details[2])+"\n");
                }
                if (Integer.parseInt(details[2])==0){ //out of stock
                    System.out.println("Clothing Item with name: "+clothingProduct.getName()+" Barcode: "+clothingProduct.getBarCode()+" is out of stock.\n");
                }
            }
            return true;

        } else {

            return false;
        }
    }
    public void showNotifications(){ //showing notification
        Set<String> keys = productInfo.stringPropertyNames();
        for (String key : keys) { //iterating over the items and outputting notification if item is out of stock or low on stock
            readProduct(Integer.parseInt(key));
        }
    }
}
