package Controllers.Customer;

import Controllers.UserSession;
import DataBase.DBLoader;
import Entities.*;

import java.util.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShoppingCartController { //This is the ShoppingCartController that interacts with the database.
    //It takes the DBLoader instance and gets all the necessary properties files from it.
    DBLoader dbLoader = DBLoader.getInstance(); //DBLoader instance
    Properties shoppingCartInfo = dbLoader.getShoppingCartInfo(); //Getting the properties file containing ShoppingCartInfo
    Properties productInfo = dbLoader.getProductInfo(); //Getting the properties file containing Product Info
    ShoppingCart shoppingCart; //Shopping Cart instance

    public void updateProp() //This is used to update the Properties file after modifying it as the Properties file doesn't update automatically
    {
        try (FileOutputStream outputStream = new FileOutputStream(DBLoader.SHOPPINGCARTFILE)) {
            shoppingCartInfo.store(outputStream, null); //Storing Changes
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shoppingCartInfo.clear(); // Clears existing properties
        try (FileInputStream inputStream = new FileInputStream(DBLoader.SHOPPINGCARTFILE)) {
            shoppingCartInfo.load(inputStream); //Reloading the property to load changes
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbLoader.setShoppingCartInfo(shoppingCartInfo); //Updating the properties instance

    }
    public ShoppingCart createShoppingCart() {//Creating ShoppingCart Instance by loading info from Database
        updateProp();
        Set<String> keys = shoppingCartInfo.stringPropertyNames();
        List<Product> productList = new ArrayList<>();
        for (String key : keys) {
            String shoppingCartDetails = shoppingCartInfo.getProperty(key); //Getting the String Value of the key
            String[] details = (shoppingCartDetails.split(",")); //Splitting the String Value of the key since the attributes of the product are stored in a string seperated by ','
            if (details[0].equals("Electronics")) {
                ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1], Integer.parseInt(key), Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);
                productList.add(electronicsProduct); //Creating and adding the electronics product to the product list
            } else if (details[0].equals("Clothing")) {
                ClothingProduct clothingProduct = new ClothingProduct(details[1], Integer.parseInt(key), Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);
                productList.add(clothingProduct);//Creating and adding the clothing product to the product list
            }
        }
        ShoppingCart shoppingCart = new ShoppingCart(productList); //Creating the new ShoppingCart instance by after loading all the products in a product list
        this.shoppingCart = shoppingCart; //updating the shoppingCart Instance
        return shoppingCart;
    }

    public void deleteItemFromShoppingCart(int barcode,int i) { //Deleting item from product list of the shoppingCart and from ShoppingCart Database
        createShoppingCart();
        List<Product> productList = shoppingCart.getProductList(); //getting the product list
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getBarCode() == barcode) { //found the wanted product
                iterator.remove(); //removing it from the list
                shoppingCart.setProductList(productList);
                updateProp();
                String productDetails = shoppingCartInfo.getProperty(Integer.toString(barcode));
                if (productDetails != null) { //found the product in database
                    shoppingCartInfo.remove(String.valueOf(barcode)); //removing it from database
                    if (i==1) //When you want output.
                        System.out.println("Product Removed from Shopping Cart!\n");
                    updateProp();
                } else //product not found in database
                    System.out.println("Product Not Found\n");
            }
        }
    }
    public void readAllProducts(){ //Reading all products from shopping cart database and printing them
        createShoppingCart();
        updateProp();
        for (int i = 0; i < shoppingCart.getProductList().size(); ++i) {
            System.out.println(i+1+"- Name:"+ shoppingCart.getProductList().get(i).getName()+" Price: "+shoppingCart.getProductList().get(i).getPrice()+" Quantity: "+shoppingCart.getProductList().get(i).getInStock());
        }
    }
    public boolean readProduct(int barCode) { //reading a single product from database (the key is the barcode (ID))
        updateProp();
        String productDetails = shoppingCartInfo.getProperty(Integer.toString(barCode));

        if (productDetails != null) {
            String[] details = (productDetails.split(","));  //Splitting the String Value of the key since the attributes of the product are stored in a string seperated by ','
            if (details[0].equals("Electronics")) {
                ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1], barCode, Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);
                shoppingCart.getProductList().add(electronicsProduct); //Creating and adding the electronics product to the product list
            } else if (details[0].equals("Clothing")) {
                ClothingProduct clothingProduct = new ClothingProduct(details[1], barCode, Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);
                shoppingCart.getProductList().add(clothingProduct); //Creating and adding the clothing product to the product list
            }
            return true;

        } else {
            System.out.println("Product Not Found\n");
            return false;
        }
    }


    public void addProduct(int barCode, int quantity) //Adding product to shopping cart by ID and quantity
    {
        createShoppingCart();
        String productDetails = shoppingCartInfo.getProperty(Integer.toString(barCode)); //reading item from database
        if (productDetails != null) { //The item already exists, so we have to only increase the quantity
            List<Product> productList = shoppingCart.getProductList();
            Iterator<Product> iterator = productList.iterator();
            while (iterator.hasNext()) { //finding the item in the product list
                Product product = iterator.next();
                if (product.getBarCode() == barCode) { //found the item by barcode
                    int temp = product.getInStock() + quantity;
                    product.setInStock(temp); //setting the new quantity of the product in the productList
                    updateProp();
                    String[] details = (productDetails.split(",")); //Splitting the String Value of the key since the attributes of the product are stored in a string seperated by ','
                    if (details[0].equals("Electronics")) { //setting the new quantity of the electronics item in the database
                        ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1], barCode, Integer.parseInt(details[2]), temp, details[4], details[5]);
                        shoppingCartInfo.setProperty(Integer.toString(electronicsProduct.getBarCode()), "Electronics," + electronicsProduct.getName() + "," + Integer.toString(electronicsProduct.getPrice()) + "," + Integer.toString(temp) + "," + electronicsProduct.getType() + "," + electronicsProduct.getBrand());
                        updateProp();
                    } else if (details[0].equals("Clothing")) { //setting the new quantity of the clothing item in the database
                        ClothingProduct clothingProduct = new ClothingProduct(details[1], barCode, Integer.parseInt(details[2]), temp, details[4], details[5]);
                        shoppingCartInfo.setProperty(Integer.toString(clothingProduct.getBarCode()), "Clothing," + clothingProduct.getName() + "," + Integer.toString(clothingProduct.getPrice()) + "," + Integer.toString(temp) + "," + clothingProduct.getType() + "," + clothingProduct.getSize());
                        updateProp();
                    }

                }
            }
        }
        else //the item doesn't exist in the shoppingCart database so we have to create it
        {
            updateProp();
            productDetails = productInfo.getProperty(Integer.toString(barCode));
            if (productDetails != null) {
                String[] details = (productDetails.split(",")); //Splitting the String Value of the key since the attributes of the product are stored in a string seperated by ','
                if (details[0].equals("Electronics")) {
                    ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1], barCode, Integer.parseInt(details[2]), quantity, details[4], details[5]); //creating the product
                    shoppingCart.getProductList().add(electronicsProduct); //Adding product to product list
                    shoppingCartInfo.setProperty(Integer.toString(electronicsProduct.getBarCode()), "Electronics," + electronicsProduct.getName() + "," + Integer.toString(electronicsProduct.getPrice()) + "," + Integer.toString(electronicsProduct.getInStock()) + "," + electronicsProduct.getType() + "," + electronicsProduct.getBrand());
                    updateProp(); //adding product to database and updating it
                } else if (details[0].equals("Clothing")) {
                    ClothingProduct clothingProduct = new ClothingProduct(details[1], barCode, Integer.parseInt(details[2]), quantity, details[4], details[5]); //creating the product
                    shoppingCart.getProductList().add(clothingProduct); //Adding product to product list
                    shoppingCartInfo.setProperty(Integer.toString(clothingProduct.getBarCode()), "Clothing," + clothingProduct.getName() + "," + Integer.toString(clothingProduct.getPrice()) + "," + Integer.toString(clothingProduct.getInStock()) + "," + clothingProduct.getType() + "," + clothingProduct.getSize());
                    updateProp(); //adding product to database and updating it
                }

            }
        }
        System.out.println("Product Added\n");


    }
    public void updateProduct(int barCode,int quantity) //Updating the product in the shopping cart by quantity
    {
        createShoppingCart(); //Loading the shoppingcart
        String productDetails = shoppingCartInfo.getProperty(Integer.toString(barCode));
        if (productDetails != null) { //found item in database
            List<Product> productList = shoppingCart.getProductList();//getting productlist
            Iterator<Product> iterator = productList.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getBarCode() == barCode) { //found item in productlist
                    int temp = quantity;
                    product.setInStock(temp); //setting new quantity in productlist
                    updateProp();
                    String[] details = (productDetails.split(",")); //splitting the string value of the key.
                    if (details[0].equals("Electronics")) {
                        ElectronicsProduct electronicsProduct = new ElectronicsProduct(details[1], barCode, Integer.parseInt(details[2]), temp, details[4], details[5]);
                        shoppingCartInfo.setProperty(Integer.toString(electronicsProduct.getBarCode()), "Electronics," + electronicsProduct.getName() + "," + Integer.toString(electronicsProduct.getPrice()) + "," + Integer.toString(temp) + "," + electronicsProduct.getType() + "," + electronicsProduct.getBrand()); //creating electronics product with new quantity
                        updateProp(); //setting new quantity in database
                    } else if (details[0].equals("Clothing")) {
                        ClothingProduct clothingProduct = new ClothingProduct(details[1], barCode, Integer.parseInt(details[2]), temp, details[4], details[5]);
                        shoppingCartInfo.setProperty(Integer.toString(clothingProduct.getBarCode()), "Clothing," + clothingProduct.getName() + "," + Integer.toString(clothingProduct.getPrice()) + "," + Integer.toString(temp) + "," + clothingProduct.getType() + "," + clothingProduct.getSize());//creating electronics product with new quantity
                        updateProp();//setting new quantity in database
                    }
                }
            }
        }
        System.out.println("Product Updated\n");

    }

    public void orderProduct(int barCode, UserSession userSession){//Ordering the product
        updateProp();
        String productCartDetails = shoppingCartInfo.getProperty(Integer.toString(barCode)); //getting shoppingCartInfo from database
        String productDetails = productInfo.getProperty(Integer.toString(barCode)); //getting productInfo from database
        ElectronicsProduct electronicsProductCart = null; //Electronics Cart Product
        ElectronicsProduct electronicsProduct = null; //Electronics Product
        ClothingProduct clothingProductCart = null; //Cart Clothing Product
        ClothingProduct clothingProduct = null; //Clothing Product
        if (productDetails != null) { //found product in Product database
            String[] details = (productDetails.split(","));
            if (details[0].equals("Electronics")) { //creating electronics product instance
                 electronicsProduct = new ElectronicsProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
            }
            else if (details[0].equals("Clothing")) { //creating clothing product instance
                 clothingProduct = new ClothingProduct(details[1],barCode,Integer.parseInt(details[2]),Integer.parseInt(details[3]),details[4],details[5] );
            }

        } else {
            System.out.println("Product Not Found\n");
        }

        if (productCartDetails != null) { //found item in shoppingcart database
            String[] details = (productCartDetails.split(","));
            if (details[0].equals("Electronics")) { //creating electronics cart product instance
                electronicsProductCart = new ElectronicsProduct(details[1], barCode, Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);

            } else if (details[0].equals("Clothing")) { //creating clothing cart product instance
                clothingProductCart = new ClothingProduct(details[1], barCode, Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4], details[5]);
            }

        } else {
            System.out.println("Product Not Found\n");
        }
        if (electronicsProductCart!=null && electronicsProduct!=null) //ordering an electronics product
        {
            if ( electronicsProductCart.getInStock()>electronicsProduct.getInStock()){ //Ordering more than there is in stock
                System.out.println("You are ordering more than there is in the inventory:\n" +
                        "Do you Wanna proceed by only buying "+electronicsProduct.getInStock()+" products ?");
                System.out.println("1- Proceed: \n " +
                        "2- Cancel Order and delete item from shopping cart: \n");
                Scanner scanner = new Scanner(System.in);
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix){
                    case 1:{ //order only what there is in stock
                        electronicsProductCart.setInStock(electronicsProduct.getInStock());//modify the quantity
                        OrderMenuController orderMenuController = new OrderMenuController(electronicsProductCart,userSession);
                        orderMenuController.showOrderMenu(); //show the order menu
                    }
                    case 2:
                    {
                        deleteItemFromShoppingCart(electronicsProductCart.getBarCode(),1); //deleting item from shopping cart and canceling order
                        ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                        productMenuCustomer.showProductMenu(); //show the product menu
                    }
                }

            }
            else { //Order passed successfully the order quantity is less than there is in stock.
                OrderMenuController orderMenuController = new OrderMenuController(electronicsProductCart,userSession);
                orderMenuController.showOrderMenu();
            }
        }
        else if (clothingProductCart!=null && clothingProduct!=null) //same thing but for clothing product
        {
            if ( clothingProductCart.getInStock()>clothingProduct.getInStock()){
                System.out.println("You are ordering more than there is in the inventory:\n" +
                        "Do you Wanna proceed by only buying "+clothingProduct.getInStock()+" products ?");
                System.out.println("1- Proceed: \n" +
                        "2- Cancel Order and delete item from shopping cart: \n");
                Scanner scanner = new Scanner(System.in);
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix)
                {
                    case 1:{
                        clothingProductCart.setInStock(clothingProduct.getInStock());
                        OrderMenuController orderMenuController = new OrderMenuController(clothingProductCart,userSession);
                        orderMenuController.showOrderMenu();
                    }
                    case 2:
                    {
                        deleteItemFromShoppingCart(clothingProductCart.getBarCode(),1);
                        ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
                        productMenuCustomer.showProductMenu();
                    }
                }

            }
            else
            {
                OrderMenuController orderMenuController = new OrderMenuController(clothingProductCart,userSession);
                orderMenuController.showOrderMenu();
            }
        }

    }
}