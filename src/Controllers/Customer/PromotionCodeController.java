package Controllers.Customer;

import DataBase.DBLoader;
import Entities.Product;

import java.util.Properties;

public class PromotionCodeController { //this is the Promotion code that interacts with PromotionCode database

    private DBLoader dbLoader = DBLoader.getInstance(); //getting DBloader instance
    private Properties promotionCodeInfo = dbLoader.getPromotionCodeInfo(); //getting the properties file that contains all the info

    public Product usePromotionCode(String promotionCode,Product product){ //applying the promo code
        String promotionCodePercent = promotionCodeInfo.getProperty(promotionCode);
        if (promotionCodePercent!=null){ //found the code in database
            System.out.println("Valid Promotion Code!");
            float reduction= (float) Integer.parseInt(promotionCodePercent) /100; //calculating reduction
            product.setPrice(Math.round(product.getPrice()*(1-reduction))); //applying the promo to the item
            product.setPromotionAdded(); //Setting the promotionAdded boolean to true
        }
        else{//didn't find the code in database
            System.out.println("Promotion Code Non Valid");
        }
        return product;
    }

}
