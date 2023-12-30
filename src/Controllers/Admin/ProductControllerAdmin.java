package Controllers.Admin;

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

public class ProductControllerAdmin { //this is the product controller of the admin that supports CRUD operations

    public List<Product> productList = new ArrayList<>(); //product list
    private DBLoader dbLoader = DBLoader.getInstance(); //data base loader
    private Properties productInfo = dbLoader.getProductInfo(); //getting the .properties file that stores the product info

    public void updateProp(){ //updating the .properties file after making changes since it doesn't update automatically
        try (FileOutputStream outputStream = new FileOutputStream(DBLoader.PRODUCTFILE)) {
            productInfo.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e); //handling exceptions when file doesn't open correctly
        }
        productInfo.clear();
        try (FileInputStream inputStream = new FileInputStream(DBLoader.PRODUCTFILE)) {
            productInfo.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e); //handling exceptions when file doesn't open correctly
        }
        dbLoader.setProductInfo(productInfo);
    }

    public boolean createElectronicsProduct(int barCode, String name, int price, int inStock, String type, String brand) { //creating the electronics product
        String test=productInfo.getProperty(Integer.toString(barCode));
        if (test!=null) //item with this barcode already exists
            return false;
        else //safe to create the new item
        {
        ElectronicsProduct electronicsProduct = new ElectronicsProduct(name, barCode, price, inStock, type, brand);
        productInfo.setProperty(Integer.toString(electronicsProduct.getBarCode()), "Electronics," + electronicsProduct.getName() + "," + Integer.toString(electronicsProduct.getPrice()) + "," + Integer.toString(electronicsProduct.getInStock()) + "," + electronicsProduct.getType() + "," + electronicsProduct.getBrand());
        productList.add(electronicsProduct); //adding item to product list
        updateProp();
        return true;
        }
    }

    public boolean createClothingProduct(int barCode, String name, int price, int inStock, String type, String size) { //creating the clothing product
        String test=productInfo.getProperty(Integer.toString(barCode));
        if (test!=null) //item with this barcode already exists
            return false;
        else { //safe to create the new item
            ClothingProduct clothingProduct = new ClothingProduct(name, barCode, price, inStock, type, size);
            productInfo.setProperty(Integer.toString(clothingProduct.getBarCode()), "Clothing," + clothingProduct.getName() + "," + Integer.toString(clothingProduct.getPrice()) + "," + Integer.toString(clothingProduct.getInStock()) + "," + clothingProduct.getType() + "," + clothingProduct.getSize());
            productList.add(clothingProduct);
            updateProp();
            return true;
        }
    }

    public boolean readProduct(int barCode) { //reading a product from database by its barCode (ID)
        updateProp();
        String productDetails = productInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) { //found the item
            String[] details = (productDetails.split(","));
            if (details[0].equals("Electronics")) { //electronics product
                ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                productList.add(electronicsProduct);
            }
            else if (details[0].equals("Clothing")) { //clothing product
                ClothingProduct clothingProduct = new ClothingProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
                productList.add(clothingProduct);
            }
            return true;

        } else {
            System.out.println("Product Not Found\n");
            return false;
        }
    }

    public void readAllProducts(){ //reading all products by iterating on database
        productList.clear();
        updateProp();
        Set<String> keys = productInfo.stringPropertyNames();

        for (String key : keys) {
            readProduct(Integer.parseInt(key)); //iterating on database
        }

        for (int i = 0; i < productList.size(); ++i) { //outputting the items
            System.out.println(i+1+"- Name:"+ productList.get(i).getName()+" Price: "+productList.get(i).getPrice());
        }


    }
    public void deleteProduct(int barCode){ //deleting item from database by its barcode
        updateProp();
        String productDetails = productInfo.getProperty(Integer.toString(barCode));
        if (productDetails!=null) //found item
        {
            productInfo.remove(String.valueOf(barCode));
            System.out.println("Product Removed Successfully!\n");
            updateProp();
        }
        else //product doesn't exist in database
            System.out.println("Product Not Found\n");

    }
    public boolean updateProduct(int barCode, String name, int price, int inStock){ //updating the product new price new name new quantity on database
        updateProp();
        String productDetails = productInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) { //found item in database
            String[] details = (productDetails.split(","));
            if (details[0].equals("Electronics")) { //updating electronics product
                productInfo.remove(String.valueOf(barCode));
                updateProp();
                createElectronicsProduct(barCode, name, price, inStock, details[4], details[5]);
                System.out.println("Product Updated Successfully!\n");
            }
            else if (details[0].equals("Clothing")) { //updating clothing product
                productInfo.remove(String.valueOf(barCode));
                updateProp();
                createClothingProduct(barCode, name, price, inStock, details[4],  details[5]);
                System.out.println("Product Updated Successfully!\n");
            }
            updateProp();
            return true;

        } else {
            System.out.println("Product Not Found\n");
            return false;
        }

    }
    public void updateProductOrdered(int barCode,int quantity){ //updating the inventory after a client has passed an order
        updateProp();
        String productDetails = productInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) { //found the item
            String[] details = (productDetails.split(","));
            if (details[0].equals("Electronics")) { //decreasing the quantity of electronics item
                productInfo.remove(String.valueOf(barCode));
                updateProp();
                createElectronicsProduct(barCode, details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3])-quantity, details[4], details[5]);
            }
            else if (details[0].equals("Clothing")) { //decreasing the quantity of clothing item
                productInfo.remove(String.valueOf(barCode));
                updateProp();
                createClothingProduct(barCode, details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3])-quantity, details[4],  details[5]);
            }
            updateProp(); //updating properties file to save changes
        }
    }
}
