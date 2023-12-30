package Controllers.Customer;

import DataBase.DBLoader;
import Entities.ClothingProduct;
import Entities.ElectronicsProduct;
import Entities.Product;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ProductControllerCustomer {// This is the productControllerCustomer

    public List<Product> productList = new ArrayList<>();
    private DBLoader dbLoader = DBLoader.getInstance();
    private Properties productInfo = dbLoader.getProductInfo();

    public void updateProp(){ //updating the database properties file
        try (FileOutputStream outputStream = new FileOutputStream(DBLoader.PRODUCTFILE)) {
            productInfo.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e); //handling exception
        }
        productInfo.clear(); // Clears existing properties
        try (FileInputStream inputStream = new FileInputStream(DBLoader.PRODUCTFILE)) {
            productInfo.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e); //handling exception
        }
        dbLoader.setProductInfo(productInfo);
    }

    public boolean readProduct(int barCode) { //Reading Product
        updateProp();
        String productDetails = productInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) {
            String[] details = (productDetails.split(",")); //splitting the String value of the key
            if (details[0].equals("Electronics")) {
                ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                if(Integer.parseInt(details[3])>0)
                    productList.add(electronicsProduct); //creating the product and adding it to product list
            }
            else if (details[0].equals("Clothing")) {
                ClothingProduct clothingProduct = new ClothingProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                if(Integer.parseInt(details[3])>0)
                    productList.add(clothingProduct);//creating the product and adding it to product list
            }
            return true;

        } else {
            System.out.println("Product Not Found\n");
            return false;
        }
    }
    public void readAllProducts(){ // reading all products from database
        productList.clear();
        updateProp();
        Set<String> keys = productInfo.stringPropertyNames();

        for (String key : keys) {
            readProduct(Integer.parseInt(key));
        }

        for (int i = 0; i < productList.size(); ++i) { //outputting the products
            System.out.println(i+1+"- Name:"+ productList.get(i).getName()+" Price: "+productList.get(i).getPrice());
        }


    }
    public void readAllProductsWithoutAffichage(){ // reading all products without output
        productList.clear();
        updateProp();
        Set<String> keys = productInfo.stringPropertyNames();

        for (String key : keys) {
            readProduct(Integer.parseInt(key));
        }
    }

}
