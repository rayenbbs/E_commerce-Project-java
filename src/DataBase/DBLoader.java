package DataBase;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class DBLoader { //This is The Database Loader Class, it is used to access and load the .properties files that store all the infos.
    //This class is implemented using the Singleton Design Pattern as this class should only be instantiated once as it loads the database once and not more.
    //This class loads the Login Info Database, the Product Database, The shopping cart database, the OrderHistory Database and the PromotionCode Database.
    private static final String LOGINFILE = Paths.get("src", "DataBase/Login_DataBase.properties").toString(); //getting the file path
    public static final String PRODUCTFILE = Paths.get("src", "DataBase/Product_DataBase.properties").toString(); //getting the file path
    public static final String SHOPPINGCARTFILE = Paths.get("src", "DataBase/ShoppingCart_DataBase.properties").toString(); //getting the file path

    public static final String ORDERFILE = Paths.get("src", "DataBase/Order_DataBase.properties").toString(); //getting the file path
    public static final String PROMOTIONCODEFILE = Paths.get("src", "DataBase/PromotionCode_DataBase.properties").toString(); //getting the file path



    private Properties loginInfo;
    private Properties productInfo;
    private Properties shoppingCartInfo;
    private Properties orderInfo;
    private Properties promotionCodeInfo;


    private static volatile DBLoader instance; //Singleton Design pattern implementation

    private DBLoader() //Singleton Design pattern implementation
    {
        loginInfo=new Properties();
        productInfo=new Properties();
        shoppingCartInfo=new Properties();
        orderInfo=new Properties();
        promotionCodeInfo=new Properties();

        try (InputStream input = new FileInputStream(LOGINFILE)) {
            loginInfo.load(input);
        } catch (IOException e) {
            System.err.println("Error loading user database: "+e.getMessage()); //Handling exceptions
        }
        try (InputStream input = new FileInputStream(PRODUCTFILE)) {
            productInfo.load(input);
        } catch (IOException e) {
            System.err.println("Error loading Product database: "+e.getMessage());//Handling exceptions
        }
        try (InputStream input = new FileInputStream(SHOPPINGCARTFILE)) {
            shoppingCartInfo.load(input);
        } catch (IOException e) {
            System.err.println("Error loading ShoppingCart database: "+e.getMessage());//Handling exceptions
        }
        try (InputStream input = new FileInputStream(ORDERFILE)) {
            orderInfo.load(input);
        } catch (IOException e) {
            System.err.println("Error loading Order database: "+e.getMessage());//Handling exceptions
        }
        try (InputStream input = new FileInputStream(PROMOTIONCODEFILE)) {
            promotionCodeInfo.load(input);
        } catch (IOException e) {
            System.err.println("Error loading Promotion Code database: "+e.getMessage());//Handling exceptions
        }
    }

    public static DBLoader getInstance(){ //Singleton Design pattern implementation
        DBLoader result=instance;
        if (result==null){
            synchronized (DBLoader.class){
                result=instance;
                if (result==null){
                    instance = result = new DBLoader();
                }
            }
        }
        return result;
    }

    public Properties getLoginInfo() {
        return (this.loginInfo);
    }
    public Properties getOrderInfo() {
        return (this.orderInfo);
    }


    public Properties getProductInfo() {
        return (this.productInfo);
    }

    public Properties getShoppingCartInfo() { return (this.shoppingCartInfo); }

    public void setProductInfo(Properties productInfo) {
        this.productInfo=productInfo;
    }
    public void setShoppingCartInfo(Properties shoppingCartInfo) {
        this.shoppingCartInfo=shoppingCartInfo;
    }
    public void setOrderInfo(Properties orderInfo) {
        this.orderInfo=orderInfo;
    }
    public Properties getPromotionCodeInfo() {
        return (this.promotionCodeInfo);
    }


}
